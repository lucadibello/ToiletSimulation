package Interfaces;

import Characters.Student;

/**
 *
 * @author Luca Di Bello
 */
public interface StudentListener {
    /**
     * Evento generato quando viene cliccato su uno studente
     * @param source Studente che ha generato l'evento
     */
    public void studentSelected(Student source);
}
