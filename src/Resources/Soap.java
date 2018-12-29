package Resources;

import Exceptions.SoapRunOutException;

/**
 * This class describes the soap of a bathroom. It can be refilled and used.
 * @author Luca Di Bello
 */
public class Soap {
    /**
    * Times that you can use the soap before it runs out. 
    */
    private int fill_status = 100;
    
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
    public void refill(){
        this.fill_status = 100;
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