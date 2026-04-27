package com.system.proyect.models;

public enum EstadoCompetencia {
    BORRADOR,    // Recién creada, no visible a estudiantes
    ACTIVA,      // Visible y con participación abierta
    FINALIZADA,  // Terminó el tiempo
    ARCHIVADA    // No se muestra, pero conserva historial
}