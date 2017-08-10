package info.spidate.main;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by root on 07.08.2017.
 */
public class Main {

    protected static String baseUrl = "https://spigotmc.org/";

    private static MySQL sql;

    private static List<String> queue = new ArrayList<>();

    public static void main(String[] args) {

        sql = new MySQL();


        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.gc();

                queue = new ArrayList<>();
                allLinks = new ArrayList<>();

                fetchResources();
                writeResources();
                Thread wait = fetchVersions();
                Thread t = new Thread() {
                    public void run() {
                        try {
                            wait.join();
                            finishQueue();
                        } catch (Exception ex) {
                            ex.printStackTrace();

                        } finally {
                            this.stop();
                        }
                    }
                };

                t.start();


            }
        }, 0, (1000 * 60) * 10);
    }

    private static List<String> allLinks;

    private static Random r = new Random();


    private static void fetchMembers(){

        int begin = 1;

        Thread t1 = new Thread(){
            public void run(){
                getAllMembers(17000, 17500, "T1");

            }
        };
        Thread t2 = new Thread(){
            public void run(){
                getAllMembers(17500, 18000, "T2");
            }
        };
        Thread t3 = new Thread(){
            public void run(){
                getAllMembers(18000, 18500, "T3");
            }
        };
        Thread t4 = new Thread(){
            public void run(){
                getAllMembers(18500, 19000, "T4");
            }
        };
        Thread t5 = new Thread(){
            public void run(){
                getAllMembers(19000, 19500, "T5");
            }
        };
        Thread t6 = new Thread(){
            public void run(){
                getAllMembers(19500, 20000, "T6");
            }
        };
        Thread t7 = new Thread(){
            public void run(){
                getAllMembers(20000, 20500, "T7");
            }
        };
        Thread t8 = new Thread(){
            public void run(){
                getAllMembers(20500, 21000, "T8");
            }
        };
        Thread t9 = new Thread(){
            public void run(){
                getAllMembers(21000, 21500, "T9");
            }
        };
        Thread t10 = new Thread(){
            public void run(){
                getAllMembers(21500, 22000, "T10");
            }
        };

        Thread t11 = new Thread(){
            public void run(){
                getAllMembers(22000, 22500, "T11");

            }
        };
        Thread t12 = new Thread(){
            public void run(){
                getAllMembers(22500, 23000, "T12");
            }
        };
        Thread t13 = new Thread(){
            public void run(){
                getAllMembers(23500, 24000, "T13");
            }
        };
        Thread t14 = new Thread(){
            public void run(){
                getAllMembers(24000, 24500, "T14");
            }
        };
        Thread t15 = new Thread(){
            public void run(){
                getAllMembers(24500, 25000, "T15");
            }
        };
        Thread t16 = new Thread(){
            public void run(){
                getAllMembers(25000, 25500, "T16");
            }
        };
        Thread t17 = new Thread(){
            public void run(){
                getAllMembers(25500, 26000, "T17");
            }
        };
        Thread t18 = new Thread(){
            public void run(){
                getAllMembers(26000, 26500, "T18");
            }
        };
        Thread t19 = new Thread(){
            public void run(){
                getAllMembers(26500, 27000, "T19");
            }
        };
        Thread t20 = new Thread(){
            public void run(){
                getAllMembers(27000, 27500, "T20");
            }
        };







        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        t12.start();
        t13.start();
        t14.start();
        t15.start();
        t16.start();
        t17.start();
        t18.start();
        t19.start();
        t20.start();


        Thread t = new Thread(){
            public void run(){
                try{
                    t1.join();
                    t2.join();
                    t3.join();
                    t4.join();
                    t5.join();
                    t6.join();
                    t7.join();
                    t8.join();
                    t9.join();
                    t10.join();
                    t11.join();
                    t12.join();
                    t13.join();
                    t14.join();
                    t15.join();
                    t16.join();
                    t17.join();
                    t18.join();
                    t19.join();
                    t20.join();
                }catch(Exception ex){

                }finally{
                    this.stop();
                    System.out.println("Done.");
                }
            }
        };


    }

    private static void fetchResources() {
        allLinks = new ArrayList<>();
        try{
            for (int i = 25; i < 50; i++) {
                Document resourceList = Jsoup.connect(baseUrl + "resources/?page=" + i).userAgent("Well-No" + r.nextInt(150)).get();

                Elements list = resourceList.getElementsByClass("resourceList");

                List<Element> allLinkse = new ArrayList<>();


                for (Element e : list) {

                    allLinkse = e.getElementsByClass("title");

                }


                for (int r = 0; r < allLinkse.size(); r++) {
                    allLinks.add(baseUrl + allLinkse.get(r).select("a").attr("href"));
                }

                System.out.println("Added page - " + i);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }



    private static void finishQueue(){
        int iduplicated = 1;
        try{
            for(int i = 0; i < queue.size(); i++){
                Document completeResource = Jsoup.connect(allLinks.get(i)).userAgent("Well-Nosdsea" + r.nextInt(15000)).get();

                Elements ver = completeResource.select("div.resourceInfo span");

                List<Element> allEle = completeResource.getElementsByClass("author");

                Element authorElement = allEle.get(0);

                String author = authorElement.text();

                String uri = allLinks.get(i);

                List<Element> titles = completeResource.select("h1");

                String title = titles.get(0).text();




                String pluginId;

                String[] urisplit = uri.split("/");

                String titleAndId = urisplit[4];


                pluginId = titleAndId.split("\\.")[1];


                author = author.split(":")[1].replace(" ", "");


                String finishedtitle;

                title = title.replace(ver.text(), "");

                finishedtitle = new String(java.nio.charset.Charset.forName("UTF-8").encode(title).array());


                finishedtitle = finishedtitle.replace("'", "");


                if(i < 42 && iduplicated != 9){

                    sql.updateResource(iduplicated, uri, author, ver.text(), finishedtitle);

                }

                sql.updateAll(pluginId, uri, author, ver.text());


                iduplicated++;
                //System.out.println("Ver - " + ver.text() + "  Author  - " + author + "  Title - " + finishedtitle + "  Link -  " + uri + "  ID - " + pluginId);

                System.out.println("[QueueMaster] Completed resource #" + i);

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }



    private static void writeResources(){
        try{
            FileWriter writer = new FileWriter("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\allResources.txt");

            writer.write("");

            for(int write = 0; write < allLinks.size(); write++){
                writer.write(allLinks.get(write) + "\n");
                System.out.println("Wrote " + write + " urls to file...");
            }
            writer.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private static long startTime, endTime;

    private static Thread fetchVersions(){


        System.out.println("Fetching versions..");

        List<Thread> allThreads = new ArrayList<>();
        try{

            Thread t1 = new Thread(){
                public void run(){
                    doThings(0, 12, "T1");
                }
            };

            Thread t2 = new Thread(){
                public void run(){
                    doThings(12, 24, "T2");
                }
            };

            Thread t3 = new Thread(){
                public void run(){
                    doThings(24, 36, "T3");
                }
            };

            Thread t4 = new Thread(){
                public void run(){
                    doThings(36, 48, "T4");
                }
            };

            Thread t5 = new Thread(){
                public void run(){
                    doThings(48, 60, "T5");
                }
            };

            Thread t6 = new Thread(){
                public void run(){
                    doThings(60, 72, "T6");
                }
            };

            Thread t7 = new Thread(){
                public void run(){
                    doThings(72, 84, "T7");
                }
            };

            Thread t8 = new Thread(){
                public void run(){
                    doThings(84, 96, "T8");
                }
            };

            Thread t9 = new Thread(){
                public void run(){
                    doThings(96, 108, "T9");
                }
            };

            Thread t10 = new Thread(){
                public void run(){
                    doThings(108, 120, "T10");
                }
            };

            Thread t11 = new Thread(){
                public void run(){
                    doThings(120, 132, "T11");
                }
            };

            Thread t12 = new Thread(){
                public void run(){
                    doThings(132, 144, "T12");
                }
            };

            Thread t13 = new Thread(){
                public void run(){
                    doThings(144, 156, "T13");
                }
            };

            Thread t14 = new Thread(){
                public void run(){
                    doThings(156, 168, "T14");
                }
            };

            Thread t15 = new Thread(){
                public void run(){
                    doThings(168, 180, "T15");
                }
            };

            Thread t16 = new Thread(){
                public void run(){
                    doThings(180, 192, "T16");
                }
            };

            Thread t17 = new Thread(){
                public void run(){
                    doThings(192, 204, "T17");
                }
            };

            Thread t18 = new Thread(){
                public void run(){
                    doThings(204, 216, "T18");
                }
            };

            Thread t19 = new Thread(){
                public void run(){
                    doThings(216, 228, "T19");
                }
            };

            Thread t20 = new Thread(){
                public void run(){
                    doThings(228, 240, "T20");
                }
            };




            Thread t21 = new Thread(){
                public void run(){
                    doThings(240, 252, "T21");
                }
            };

            Thread t22 = new Thread(){
                public void run(){
                    doThings(252, 264, "T22");
                }
            };

            Thread t23 = new Thread(){
                public void run(){
                    doThings(264, 276, "T23");
                }
            };

            Thread t24 = new Thread(){
                public void run(){
                    doThings(276, 288, "T24");
                }
            };

            Thread t25 = new Thread(){
                public void run(){
                    doThings(288, 300, "T25");
                }
            };

            Thread t26 = new Thread(){
                public void run(){
                    doThings(300, 312, "T26");
                }
            };

            Thread t27 = new Thread(){
                public void run(){
                    doThings(312, 324, "T27");
                }
            };

            Thread t28 = new Thread(){
                public void run(){
                    doThings(324, 336, "T28");
                }
            };

            Thread t29 = new Thread(){
                public void run(){
                    doThings(336, 348, "T29");
                }
            };

            Thread t30 = new Thread(){
                public void run(){
                    doThings(348, 360, "T30");
                }
            };

            Thread t31 = new Thread(){
                public void run(){
                    doThings(360, 372, "T31");
                }
            };

            Thread t32 = new Thread(){
                public void run(){
                    doThings(372, 384, "T31");
                }
            };

            Thread t33 = new Thread(){
                public void run(){
                    doThings(384, 396, "T33");
                }
            };

            Thread t34 = new Thread(){
                public void run(){
                    doThings(396, 408, "T34");
                }
            };

            Thread t35 = new Thread(){
                public void run(){
                    doThings(408, 420, "T35");
                }
            };

            Thread t36 = new Thread(){
                public void run(){
                    doThings(420, 432, "T36");
                }
            };

            Thread t37 = new Thread(){
                public void run(){
                    doThings(432, 444, "T37");
                }
            };

            Thread t38 = new Thread(){
                public void run(){
                    doThings(444, 456, "T38");
                }
            };

            Thread t39 = new Thread(){
                public void run(){
                    doThings(456, 468, "T39");
                }
            };

            Thread t40 = new Thread(){
                public void run(){
                    doThings(468, 480, "T40");
                }
            };



            startTime = System.currentTimeMillis();

            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t6.start();
            t7.start();
            t8.start();
            t9.start();
            t10.start();
            t11.start();
            t12.start();
            t13.start();
            t14.start();
            t15.start();
            t16.start();
            t17.start();
            t18.start();
            t19.start();
            t20.start();
            t21.start();
            t22.start();
            t23.start();
            t24.start();
            t25.start();
            t26.start();
            t27.start();
            t28.start();
            t29.start();
            t30.start();
            t31.start();
            t32.start();
            t33.start();
            t34.start();
            t35.start();
            t36.start();
            t37.start();
            t38.start();
            t39.start();
            t40.start();



            allThreads.add(t1);
            allThreads.add(t2);
            allThreads.add(t3);
            allThreads.add(t4);
            allThreads.add(t5);
            allThreads.add(t6);
            allThreads.add(t7);
            allThreads.add(t8);
            allThreads.add(t9);
            allThreads.add(t10);
            allThreads.add(t11);
            allThreads.add(t12);
            allThreads.add(t13);
            allThreads.add(t14);
            allThreads.add(t15);
            allThreads.add(t16);
            allThreads.add(t17);
            allThreads.add(t18);
            allThreads.add(t19);
            allThreads.add(t20);

            allThreads.add(t21);
            allThreads.add(t22);
            allThreads.add(t23);
            allThreads.add(t24);
            allThreads.add(t25);
            allThreads.add(t26);
            allThreads.add(t27);
            allThreads.add(t28);
            allThreads.add(t29);
            allThreads.add(t30);
            allThreads.add(t31);
            allThreads.add(t32);
            allThreads.add(t33);
            allThreads.add(t34);
            allThreads.add(t35);
            allThreads.add(t36);
            allThreads.add(t37);
            allThreads.add(t38);
            allThreads.add(t39);
            allThreads.add(t40);

        }catch(Exception ex){
            ex.printStackTrace();
        }




        Thread t = new Thread(){
            public void run(){
                try{
                    for(Thread t : allThreads){
                        t.join();
                        t.stop();
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }finally {
                    this.stop();
                }

            }
        };

        t.start();

        return t;


    }

    private static void doThings(int start, int end, String threadName){
        int iduplicated = 1;
        try{
            for(int i = start; i < end; i++){
                Document completeResource = Jsoup.connect(allLinks.get(i)).userAgent("Well-Nosdsea" + r.nextInt(15000)).get();

                Elements ver = completeResource.select("div.resourceInfo span");

                List<Element> allEle = completeResource.getElementsByClass("author");

                Element authorElement = allEle.get(0);

                String author = authorElement.text();

                String uri = allLinks.get(i);

                List<Element> titles = completeResource.select("h1");

                String title = titles.get(0).text();




                String pluginId;

                String[] urisplit = uri.split("/");

                String titleAndId = urisplit[4];


                pluginId = titleAndId.split("\\.")[1];


                author = author.split(":")[1].replace(" ", "");


                String finishedtitle;

                title = title.replace(ver.text(), "");

                finishedtitle = new String(java.nio.charset.Charset.forName("UTF-8").encode(title).array());


                finishedtitle = finishedtitle.replace("'", "");


                if(threadName.equalsIgnoreCase("T1") || threadName.equalsIgnoreCase("T2") ||threadName.equalsIgnoreCase("T3") || threadName.equalsIgnoreCase("T4")){
                    try{
                        sql.updateResource(iduplicated, uri, author, ver.text(), finishedtitle);
                    }catch(Exception ex){

                    }
                }

                sql.updateAll(pluginId, uri, author, ver.text());


                iduplicated++;
                //System.out.println("Ver - " + ver.text() + "  Author  - " + author + "  Title - " + finishedtitle + "  Link -  " + uri + "  ID - " + pluginId);

                System.out.println("[" + threadName + "] Completed resource #" + i);

            }

            endTime = System.currentTimeMillis();

            System.out.println("Process took - " + (endTime - startTime) / 1000 + " seconds.");
        }catch(HttpStatusException ex){
            int idup = iduplicated -1;
            if(idup > 0){
                queue.add(allLinks.get(idup));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }finally {

        }
    }


    public static void getAllMembers(int start, int end, String Threadname) {
        try {
            System.out.println("GO");

            for(int i = start; i < end; i++){
                String uri = baseUrl + "members/" + i;
                Document doc;
                try{
                    doc = Jsoup.connect(uri).get();
                }catch(Exception ex){
                    continue;
                }

                Elements userName = doc.select("meta[property=og:title]");

                String singleName = userName.get(0).attr("content");

                if(singleName.isEmpty()){
                    continue;
                }

                System.out.println("[" + Threadname + "]Saved user - " + singleName + " ID - " + i);

                sql.addUser(""+i, uri, singleName);




            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }






}
