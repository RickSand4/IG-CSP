$(document).ready(function() {
    // Variables globales
    let currentChallengeId = 1;
    let currentSection = 1;
    let sectionSeconds = 12 * 60; // 12 minutos
    let timerInterval;
    let challenges = [];

    // Inicializar
    loadChallengesBySection(currentSection);
    startSectionTimer();

    // Cargar desafíos de una sección
    function loadChallengesBySection(section) {
        $.get(`/api/challenges/section/${section}`, function(data) {
            challenges = data;
            renderChallengeList(data);
            if (data.length > 0) {
                loadChallengeById(data[0].id);
                // Actualizar tiempo límite de sección (usamos el primer challenge como referencia)
                sectionSeconds = data[0].timeLimitSeconds;
                updateTimerDisplay();
            }
        });
    }

    // Renderizar lista de problemas
    function renderChallengeList(list) {
        const $list = $('#challengeList');
        $list.empty();
        list.forEach((c, index) => {
            const statusIcon = index === 0 ? '⏳' : '🔒';
            $list.append(`
                <li class="challenge-item d-flex justify-content-between align-items-center mb-2 p-2 pixel-border" 
                    data-id="${c.id}" style="cursor:pointer;">
                    <span>${c.title}</span>
                    <span class="badge pixel-badge bg-secondary">${c.difficulty}</span>
                </li>
            `);
        });
        $('#progressCount').text(`0/${list.length}`);

        // Evento click en cada problema
        $('.challenge-item').click(function() {
            const id = $(this).data('id');
            loadChallengeById(id);
            $('.challenge-item').removeClass('active');
            $(this).addClass('active');
        });
    }

    // Cargar un desafío específico
    function loadChallengeById(id) {
        $.get(`/api/challenges/${id}`, function(challenge) {
            currentChallengeId = challenge.id;
            currentSection = challenge.sectionOrder;
            $('#challengeTitle').text(challenge.title);
            $('#challengeDesc').text(challenge.description);
            $('#exampleCode').text(`📌 ${challenge.example}`);
            $('#difficultyBadge').text(challenge.difficulty);
            $('#currentSectionLabel').text(`SECCIÓN ${challenge.sectionOrder}`);

            // Reiniciar timer si cambia de sección (opcional)
            // sectionSeconds = challenge.timeLimitSeconds;
            updateTimerDisplay();
        });
    }

    // Temporizador
    function startSectionTimer() {
        if (timerInterval) clearInterval(timerInterval);
        timerInterval = setInterval(() => {
            if (sectionSeconds > 0) {
                sectionSeconds--;
                updateTimerDisplay();
            } else {
                clearInterval(timerInterval);
                alert('⏰ ¡TIEMPO AGOTADO PARA ESTA SECCIÓN!');
                // Deshabilitar botones
                $('#runCodeBtn, #submitBtn').prop('disabled', true);
            }
        }, 1000);
    }

    function updateTimerDisplay() {
        const mins = Math.floor(sectionSeconds / 60);
        const secs = sectionSeconds % 60;
        $('#sectionTimerDisplay').text(`${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`);
    }

    // Botón RUN CODE
    $('#runCodeBtn').click(function() {
        const code = $('#codeEditor').val();
        const submission = {
            challengeId: currentChallengeId,
            code: code
        };

        $.ajax({
            url: '/api/submissions',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(submission),
            success: function(response) {
                const isCorrect = response.correct;
                const msgClass = isCorrect ? 'text-success' : 'text-danger';
                const icon = isCorrect ? '✅' : '❌';
                $('#resultMessage').html(`<span class="${msgClass} pixel-badge">${icon} ${isCorrect ? 'PASÓ' : 'FALLÓ'}</span>`);

                if (isCorrect) {
                    // Marcar como completado en la lista
                    $(`.challenge-item[data-id="${currentChallengeId}"] .badge`).text('✔️');
                }
            },
            error: function() {
                $('#resultMessage').html('<span class="text-danger">Error al enviar</span>');
            }
        });
    });

    // Botón SUBMIT (similar a RUN)
    $('#submitBtn').click(function() {
        $('#runCodeBtn').click();
    });

    // Botón SIGUIENTE
    $('#nextBtn').click(function() {
        // Buscar siguiente challenge en la lista actual
        const nextChallenge = challenges.find(c => c.id > currentChallengeId);
        if (nextChallenge) {
            loadChallengeById(nextChallenge.id);
        } else {
            // Si es el último de la sección, pasar a siguiente sección
            const nextSection = currentSection + 1;
            $.get(`/api/challenges/section/${nextSection}`, function(data) {
                if (data.length > 0) {
                    currentSection = nextSection;
                    challenges = data;
                    renderChallengeList(data);
                    loadChallengeById(data[0].id);
                    sectionSeconds = data[0].timeLimitSeconds;
                    updateTimerDisplay();
                } else {
                    alert('🎉 ¡Has completado todas las secciones!');
                }
            }).fail(function() {
                alert('No hay más secciones disponibles.');
            });
        }
    });

    // Simular temporizador global (estático)
    function updateGlobalTimer() {
        // No implementado en este ejemplo, se puede añadir.
    }
});