package org.kodluyoruz;

import java.util.ArrayList;
import java.util.List;

public class Restoran {

    private int masa = 5;
    private List<Integer> sef = new ArrayList<>(2);
    private List<Integer> garson = new ArrayList<>(3);
    private List<Integer> musteri = new ArrayList<>();

    private Object lock1 = new Object();
    private Object lock2 = new Object();
    private Object lock3 = new Object();

    public void servisYap(){

        synchronized (lock1){
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                    garson.add(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (masa == 5){
                    System.out.println("Servis yapılıyor...");
                    garson.clear();
                }else {
                    garson.add(i);
                    if (garson.size() > 3){
                        garson.remove(garson.size()-1);
                    }
                }
            }

        }

    }

    public void yemekYap(){

        synchronized (lock2){
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(1000);
                    sef.add(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(masa > 0){
                    System.out.println("Yemek yapılıyor...");
                }
            }


        }

    }

    public void yemekYe(){
        synchronized (lock3){
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    musteri.add(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (masa == 5){
                    System.out.println("Müşteri yok ver yar");
                    masa-=1;
                    musteri.clear();
                }else {
                    System.out.println("Müşteri var yer kalmadı.");
                    musteri.add(i);
                }
                if (musteri.size() > 5){
                    System.out.println("Sırada bekleyenler var.");
                    musteri.add(i);
                }
            }


        }

    }


    public void calistir(){
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                yemekYe();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                yemekYap();
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                servisYap();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Müşteri sayısı:"+ musteri.size()+" Garson sayısı:"+garson.size()+" Sef sayısı:"+sef.size());
        System.out.println("Bekleyen Müşteri Sayısı:"+(musteri.size()-5));

    }
}
