public class DoJob implements Runnable {
    
  private final String sentence;
 
  DoJob(String sentence) {this.sentence = sentence;}
 
  public void run() {
    try {
       String[] words = sentence.split("\\W+");
       for(String s: words){
           //global listede yoksa 1 yaz, varsa 1 artır
           Integer g = (Integer) Main.global.get(s);
           Main.global.put(s, g == null ? 1 : g+1 );
       }
       
       //her bir cümledeki toplam kelime sayısını topla
       Main.sumValue += words.length;
    }
    catch (Exception e) {
       System.out.println
           (Thread.currentThread().getName() + " " + e.getMessage());
    }
  }
}