package com.anyemi.omrooms.Utils;

import android.util.Log;

import com.anyemi.omrooms.Model.RoomDetails;
import com.anyemi.omrooms.Model.RoomFacility;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.CalenderActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConverterUtil implements ConstantFields{

    public static long ConvertDateToSetOnCalender(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long dateTosetOnCalender = 0;
        try {
            Date date1 = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
//            calendar.add(Calendar.DAY_OF_YEAR, +1);
//            Date newDate = calendar.getTime();
//            String newDateF = dateFormat.format(newDate);
            dateTosetOnCalender = calendar.getTime().getTime();
            Log.e("Convertor",""+dateTosetOnCalender+" p day: "+date);

//            CalenderActivity.checkOut=newDateF;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTosetOnCalender;
    }

    public static String setTodaysDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = null;
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        date= dateFormat.format(date1);
        return date;

    }

    public static String setDefaultCheckOutDateToNextDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String nextDay = null;
        try {
            Date date1 = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            Date newDate = calendar.getTime();
            nextDay = dateFormat.format(newDate);
            Log.e("next day a",""+nextDay+" p day a: "+date);

//            return newDateF;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDay;

    }

    public static boolean checkCurrentDateIsLessThenSaved(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        try {
            Date savedDate = dateFormat.parse(date);
            calendar.setTime(savedDate);
            Date sDate = calendar.getTime();

            long diff = sDate.getTime() - currentDate.getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            Log.e("diff",""+days);
            if(days>=0){
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }

    public static int noOfDays(String checkInDate, String checkOutDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        long days = 0;
        try {
            Date date1 = dateFormat.parse(checkInDate);
            calendar.setTime(date1);
            Date inDate = calendar.getTime();

            Date date2 = dateFormat.parse(checkOutDate);
            calendar.setTime(date2);
            Date outDate = calendar.getTime();

            long diff = outDate.getTime() - inDate.getTime();
            days = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

            return (int) days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) days;
    }

    public static List<RoomFacility> checkFacilityAvailable(List<RoomDetails> roomDetails) {
        List<RoomFacility> roomFacilities = new ArrayList<>();

        for(int i=0;i<roomDetails.size();i++){
            RoomDetails roomDetails1 = roomDetails.get(i);

            if(roomDetails1.getAc().equals("1")){
                RoomFacility f = new RoomFacility(ac,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getNon_ac().equals("1")){
                RoomFacility f = new RoomFacility(nonAc,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getTv().equals("1")){
                RoomFacility f = new RoomFacility(tv,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getWheelchair().equals("1")){
                RoomFacility f = new RoomFacility(wheelChair,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getBar().equals("1")){
                RoomFacility f = new RoomFacility(bar,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getLaptop_friendly().equals("1")){
                RoomFacility f = new RoomFacility(laptopFriendly,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getBanquet_hall().equals("1")){
                RoomFacility f = new RoomFacility(banqueHall,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getRoom_heater().equals("1")){
                RoomFacility f = new RoomFacility(room_heater,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getDinning_area().equals("1")){
                RoomFacility f = new RoomFacility(dinning_area,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getMini_fridge().equals("1")){
                RoomFacility f = new RoomFacility(mini_fridge,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getPower_backup().equals("1")){
                RoomFacility f = new RoomFacility(power_backup,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getElevator().equals("1")){
                RoomFacility f = new RoomFacility(elevator,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getSwimming_pool().equals("1")){
                RoomFacility f = new RoomFacility(swimming_pool,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getPre_book_meal().equals("1")){
                RoomFacility f = new RoomFacility(pre_book_meal,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getParking_facility().equals("1")){
                RoomFacility f = new RoomFacility(parking_facility,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getFree_wifi().equals("1")){
                RoomFacility f = new RoomFacility(free_wifi,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getCard_payment().equals("1")){
                RoomFacility f = new RoomFacility(card_payment,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getGym().equals("1")){
                RoomFacility f = new RoomFacility(gym,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getHair_dryer().equals("1")){
                RoomFacility f = new RoomFacility(hair_dryer,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getLaundry().equals("1")){
                RoomFacility f = new RoomFacility(laundry,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }
            if(roomDetails1.getPet_friendly().equals("1")){
                RoomFacility f = new RoomFacility(pet_friendly,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getCctv_camera().equals("1")){
                RoomFacility f = new RoomFacility(cctv_camera,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getGeyser().equals("1")){
                RoomFacility f = new RoomFacility(geyser,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getConference_room().equals("1")){
                RoomFacility f = new RoomFacility(conference_room,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }

            if(roomDetails1.getPay_at_hotel().equals("1")){
                RoomFacility f = new RoomFacility(pay_at_hotel,roomDetails1.getRoom_type());
                if(!roomFacilities.contains(f)){
                    roomFacilities.add(f);
                }
            }



        }
        return roomFacilities;
    }

    public static int getFacilityImageResourceId(String facilityType){
        if(facilityType.equals(ac)){
            return R.drawable.ic_ac_unit;
        }
        if (facilityType.equals(cctv_camera)){
            return R.drawable.ic_cctv;
        }
        if(facilityType.equals(tv)){
            return R.drawable.ic_tv;
        }
        return R.drawable.ic_wifi;
    }

}
