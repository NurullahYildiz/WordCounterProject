import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    
    static HashMap<String, Integer> global = new HashMap<>();
    static Integer sumValue = 0;
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Dosya dizinini giriniz: ");
        String path = sc.nextLine();
        
        System.out.print("Yardımcı thread sayısını giriniz: ");
        int thrCount =sc.nextInt();
        
        RunWordCounter(path, thrCount);
        
        System.out.println("--Programdan cıkılıyor--");
        
    }
    
    public static void RunWordCounter(String file1, int count){
        
        ExecutorService executor = Executors.newFixedThreadPool(count);
        String[] sSentence = null;
        
        File file = new File(file1);
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(file);
			byte[] cValue = new byte[(int) file.length()];
			fileInputStream.read(cValue);
			fileInputStream.close();
 
			String fileContent = new String(cValue, "UTF-8");
                        sSentence =fileContent.split("(?<=[a-z])[\\.?!]\\s*");
                        
		} catch (IOException e) {
			e.printStackTrace();
		}    
        
        for (int i = 0; i < sSentence.length; i++) {
			String sentence = sSentence[i];
			Runnable worker = new DoJob(sentence);
			executor.execute(worker);
		}   
        
        executor.shutdown();
        
	// Thread'ler bitene kadar bekle!
	while (!executor.isTerminated()) {
 
	}
        
        Map<String, Integer> newMap = sortByValue(global);
        
        System.out.println("***********************************************************");
        System.out.println("Cümle sayısı: "+sSentence.length);
        System.out.println("***********************************************************");
        System.out.println("Ortalama Kelime Sayısı: " + (sumValue/sSentence.length) );
        System.out.println("***********************************************************");
        System.out.println("Global Liste yazdırılıyor...");
        System.out.println("***********************************************************");
        
        for (Map.Entry<String, Integer> entrySet : newMap.entrySet()) {
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            
            System.out.println
          ("Kelime: " + key + " --- Sayac: "+value.toString());
            
        }
    }
    
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> global) 
    { 
        // HashMap elemanlarını listeye al
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(global.entrySet()); 
   
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
          
        // Sıralanmış listeyi HashMap'e ekle   
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> element : list) { 
            temp.put(element.getKey(), element.getValue()); 
        } 
        return temp; 
    } 
    
}
