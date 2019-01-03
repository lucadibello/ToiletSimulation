package Resources;

import Exceptions.SoapRunOutException;

/**
 * This class describes the soap of a bathroom. It can be refilled and used.
 * @author Luca Di Bello
 */
public class Soap {
   
    public final int FILL_STANDARD = 100;

    /**
    * Times that you can use the soap before it runs out. 
    */
    private int fill_status = FILL_STANDARD;
    
    /**
     * This method is used for get the current fill status of the soap.
     * @return Current soap fill status.
     */
    public int getFillStatus(){
        return this.fill_status;
    }
    
    /**
     * This method is used for refilling the soap container. 
     */
    public synchronized void refill(){
        this.fill_status = FILL_STANDARD;
    }
    
    /**
     * With this method the soap can be used.
     * @throws SoapRunOutException Exceptions thrown when the soap it's ran out.
     */
    public synchronized void useSoap() throws SoapRunOutException{
        if(fill_status > 0){
            this.fill_status--;
        }
        else{
            throw new SoapRunOutException();
        }
    }
    
    @Override
    public String toString() {
        return "Soap container - " + fill_status + " uses left";
    }
}