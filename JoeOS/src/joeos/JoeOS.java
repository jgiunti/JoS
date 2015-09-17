/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeos;

import java.io.IOException;
import joeos.ProcessManagement.Controller.Processes;

/**
 *
 * @author Joe
 */
public class JoeOS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Processes.intitialize();
    }
    
}
