/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pizza;

import java.util.concurrent.Semaphore;

/**
 *
 * @author MD saifullah
 */
public class multiplemutex {

    static Semaphore mutex1 = new Semaphore(1, true);
    static Semaphore mutex2 = new Semaphore(1, true);
    static int oven = 0;
    static int qcd = 0;

    static class PersonA extends Thread {

        String name = "";

        PersonA(String name) {
            this.name = name;
        }

        public void run() {
            while (true) {
                try {
                    if (oven == 0 && qcd == 0) {
                        mutex1.acquire();
                        oven++;
                        System.out.println(name + " : puts pizza into oven!");
                        //Thread.sleep(5000);
                        mutex1.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class PersonB extends Thread {

        String name = "";

        PersonB(String name) {
            this.name = name;
        }

        public void run() {
            while (true) {
                try {
                    if (oven == 1) {
                        mutex1.acquire();
                        oven--;
                        System.out.println(name + " : takes pizza from oven...");
                        System.out.println(name + " : takes to QCD!");
                        //Thread.sleep(1000);
                        System.out.println("Value of count:B" + oven);
                        mutex1.release();
                        System.out.println("Value of count1:B" + oven);
                    }

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }

    }

    static class PersonC extends Thread {

        String name = "";

        PersonC(String name) {
            this.name = name;
        }

        public void run() {
            while (true) {
                try {
                    if (qcd == 1) {
                        mutex2.acquire();
                        System.out.println(name + " : takes pizza to customers!");
                        System.out.println("Value of count:C" + qcd);
                        qcd = 0;
                        //Thread.sleep(2000);
                        mutex2.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {

        PersonC t3 = new PersonC("C");
        t3.start();

        PersonA t1 = new PersonA("A");
        t1.start();

        PersonB t2 = new PersonB("B");
        t2.start();

    }
}
