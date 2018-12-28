package Resources;

import Exceptions.PaperTowelRunOut;

/**
 * This class describes the container of the paper towels.
 * @author Luca Di Bello
 */
public class PaperTowel {
    /**
    * Remaining sheets of paper. 
    */
    private int fill_status = 100;
    
    /**
     * This method is used for get how many sheets of paper are remaining.
     * @return Number of the remaining sheets of paper.
     */
    public int getFillStatus(){
        return this.fill_status;
    }
    
    /**
     * This method is used for refilling the paper towel container. 
     */
    public void refill(){
        this.fill_status = 100;
    }
    
    /**
     * This method is used for grab a sheet of paper.
     * @throws PaperTowelRunOut Exception thrown when the paper towel container is empty.
     */
    public synchronized void grabPaper() throws PaperTowelRunOut{
        if(fill_status > 0){
            this.fill_status--;
        }
        else{
            throw new PaperTowelRunOut();
        }
    }
}