package SJmp3.Utils.Mp3Converter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExtProgRun {

 public static void gogo(String sss) 
  throws IOException, InterruptedException {
 
  // указываем в конструкторе ProcessBuilder,
  // что нужно запустить программу ls с параметрами -l /dev
  ProcessBuilder procBuilder = new ProcessBuilder(sss);  
   
  // перенаправляем стандартный поток ошибок на
  // стандартный вывод
  procBuilder.redirectErrorStream(true);
  
  // запуск программы
  Process process = procBuilder.start();
  
  // читаем стандартный поток вывода
  // и выводим на экран
  InputStream stdout = process.getInputStream();
  InputStreamReader isrStdout = new InputStreamReader(stdout);
  BufferedReader brStdout = new BufferedReader(isrStdout);
 
  String line = null;
  while((line = brStdout.readLine()) != null) {
   System.out.println(line);
  }
   
  // ждем пока завершится вызванная программа
  // и сохраняем код, с которым она завершилась в 
  // в переменную exitVal
  int exitVal = process.waitFor();
 }    
}
