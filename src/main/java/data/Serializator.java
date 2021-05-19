package data;
import java.io.*;

/**
 * Clasa ce contine metode genereice de serializare si deserializare
 * @param <T> tipul
 * @author Birlutiu Claudiu-Andrei, CTI-ro 30226
 */
public class Serializator<T> {

    /**
     * Metoda de serializare
     * @param t obiectul de serializat
     * @param name numele fisierului
     */
    public void save(T t,String name){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(name+".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(t);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in "+name+".ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Metoda de deserializare
     * @param name nume fisier
     * @return obiectul deserializat
     * @throws IOException  exceptie de deserializare
     * @throws ClassNotFoundException  clasa negasita
     */
    public T loadData(String name) throws IOException, ClassNotFoundException {
        T input=null;
        FileInputStream fileIn = new FileInputStream(name+".ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        input = (T) in.readObject();
        in.close();
        fileIn.close();
        return input;
    }
}
