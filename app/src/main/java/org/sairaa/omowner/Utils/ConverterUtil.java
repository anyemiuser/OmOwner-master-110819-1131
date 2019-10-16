package org.sairaa.omowner.Utils;

import android.util.Log;

import org.sairaa.omowner.R;
import org.sairaa.omowner.RoomUtility.Model.RoomUtility;
import org.sairaa.omowner.RoomUtility.Model.UtilitiyDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ConverterUtil implements Constants, ConstantFields{

    public static String getTodaysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); //2019-05-09
        String date = null;
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        date = dateFormat.format(date1);
        return date;

    }

    public static String getBookingTypeString(String bookingType) {
        String typeBookingText = "";
        switch (bookingType){
            case upComingType:
                typeBookingText =  upComingText;
                break;
            case inHouseType:
                typeBookingText = inHouseText;
                break;
            case completedType:
                typeBookingText = completedText;
                break;
            case cancelledType:
                typeBookingText = cancelledText;
        }
        return typeBookingText;
    }

    public static String getCheckInCheckOutType(String type) {
        String typeBookingText = "";
        switch (type){
            case upComingType:
                typeBookingText =  UpcomingCheckIn;
                break;
            case inHouseType:
                typeBookingText = InHouseCheckOut;
                break;
            case completedType:
                typeBookingText = CompletedInActiveButton;
                break;
            case cancelledType:
                typeBookingText = CancelledInActiveButton;
                break;
        }
        return typeBookingText;
    }

    public static String getTodaysDefaultDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy",Locale.getDefault());
        String date = null;
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        date = dateFormat.format(date1);
        return date;

    }

    public static String getDefaultCheckOutDateToNextDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        String nextDay = null;
        try {
            Date date1 = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            Date newDate = calendar.getTime();
            nextDay = dateFormat.format(newDate);
            Log.e("next day a", "" + nextDay + " p day a: " + date);

//            return newDateF;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDay;

    }

    public static boolean checkCurrentDateIsLessThenSaved(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        try {
            Date savedDate = dateFormat.parse(date);
            calendar.setTime(savedDate);
            Date sDate = calendar.getTime();

            long diff = sDate.getTime() - currentDate.getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            Log.e("diff", "" + days+diff);
            if (diff <= 0) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }

    public static long ConvertDateToSetOnCalender(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        long dateTosetOnCalender = 0;
        try {
            Date date1 = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
//            calendar.add(Calendar.DAY_OF_YEAR, +1);
//            Date newDate = calendar.getTime();
//            String newDateF = dateFormat.format(newDate);
            dateTosetOnCalender = calendar.getTime().getTime();
            Log.e("Convertor", "" + dateTosetOnCalender + " p day: " + date);

//            CalenderActivity.checkOut=newDateF;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTosetOnCalender;
    }

    public static String parseDateToddMMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd/MMM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

  /*  public static String parseDateToddMMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }*/

    public static int noOfDays(String checkInDate, String checkOutDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
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
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            return (int) days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) days;
    }


    public static String parseDateToyyyymmdd(String time) {
        String inputPattern =  "dd/MMM/yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }

    public static boolean isDateToday(String from_date) {
        return getTodaysDate().equals(from_date);
    }

    public static boolean getChecked(String status) {
        if(status != null){
            switch (status){
                case "1":
                    return true;

                case "0":
                    return false;

                default:
                    return false;

            }
        }else {
            return false;
        }

    }

    public static String getCheckedString(boolean checked) {
        if(checked){
            return "1";
        }else {
            return "0";
        }
    }

    public static int getFacilityImageResourceId(String facilityType) {
        if (facilityType.equals(ac)) {
            return R.drawable.ic_air_conditioner;
        }
        if (facilityType.equals(cctv_camera)) {
            return R.drawable.ic_cctv;
        }
        if (facilityType.equals(tv)) {
            return R.drawable.ic_tv;
        }
        if (facilityType.equals(bar)) {
            return R.drawable.ic_bar;
        }
        if (facilityType.equals(laptopFriendly)) {
            return R.drawable.ic_laptop;
        }
        if (facilityType.equals(banqueHall)) {
            return R.drawable.ic_banquet_hall;
        }
        if (facilityType.equals(room_heater)) {
            return R.drawable.ic_heater;
        }
        if (facilityType.equals(dinning_area)){
            return R.drawable.ic_dining_area;
        }
        if (facilityType.equals(mini_fridge)){
            return R.drawable.ic_mini_fridge;
        }
        if (facilityType.equals(power_backup)){
            return R.drawable.ic_power_backup;
        }
        if (facilityType.equals(elevator)){
            return R.drawable.ic_elevator;
        }
        if (facilityType.equals(parking_facility)){
            return R.drawable.ic_parking_facility;
        }
        if (facilityType.equals(card_payment)){
            return R.drawable.ic_card_payment;
        }
        if (facilityType.equals(laundry)){
            return R.drawable.ic_laundry;
        }
        if (facilityType.equals(conference_room)){
            return R.drawable.ic_room_conference;
        }
        if (facilityType.equals(swimming_pool)){
            return R.drawable.ic_swimming_pool;
        }
        if (facilityType.equals(geyser)){
            return R.drawable.ic_geyser;
        }
        return R.drawable.ic_wifi;
    }

    public static List<RoomUtility> getUtilList(UtilitiyDetails utilList) {
        List<RoomUtility> roomUtilities = new ArrayList<>();
        roomUtilities.add(new RoomUtility(ac,utilList.getAc()));
        roomUtilities.add((new RoomUtility(banqueHall,utilList.getBanquet_hall())));
        roomUtilities.add(new RoomUtility(bar,utilList.getBar()));
        roomUtilities.add(new RoomUtility(card_payment,utilList.getCard_payment()));
        roomUtilities.add(new RoomUtility(cctv_camera,utilList.getCctv_camera()));
        roomUtilities.add(new RoomUtility(conference_room,utilList.getConference_room()));
        roomUtilities.add(new RoomUtility(dinning_area,utilList.getDinning_area()));
        roomUtilities.add(new RoomUtility(elevator,utilList.getElevator()));
        roomUtilities.add(new RoomUtility(free_wifi,utilList.getFree_wifi()));
        roomUtilities.add(new RoomUtility(gym,utilList.getGym()));
        roomUtilities.add(new RoomUtility(geyser,utilList.getGeyser()));
        roomUtilities.add(new RoomUtility(hair_dryer,utilList.getHair_dryer()));
        roomUtilities.add(new RoomUtility(laundry,utilList.getLaundry()));
        roomUtilities.add(new RoomUtility(laptopFriendly,utilList.getLaptop_friendly()));
        roomUtilities.add(new RoomUtility(mini_fridge,utilList.getMini_fridge()));
        roomUtilities.add(new RoomUtility(nonAc,utilList.getNon_ac()));
        roomUtilities.add(new RoomUtility(parking_facility,utilList.getParking_facility()));
        roomUtilities.add(new RoomUtility(power_backup,utilList.getPower_backup()));
        roomUtilities.add(new RoomUtility(pre_book_meal,utilList.getPre_book_meal()));
        roomUtilities.add(new RoomUtility(pay_at_hotel,utilList.getPay_at_hotel()));
        roomUtilities.add(new RoomUtility(pet_friendly,utilList.getPet_friendly()));
        roomUtilities.add(new RoomUtility(room_heater,utilList.getRoom_heater()));
        roomUtilities.add(new RoomUtility(swimming_pool,utilList.getSwimming_pool()));
        roomUtilities.add(new RoomUtility(tv,utilList.getTv()));
        roomUtilities.add(new RoomUtility(wheelChair,utilList.getWheelchair()));

        return roomUtilities;
    }

    public static void updateUtilityDetails(List<UtilitiyDetails> utilityList, RoomUtility utility, String roomType) {

        for(UtilitiyDetails util:utilityList){
            if(util.getRoom_type().equals(roomType)){
                if(utility.getFacility().equals(ac)){
                    util.setAc(utility.getOnOff());
                }else if(utility.getFacility().equals(banqueHall)){
                    util.setBanquet_hall(utility.getOnOff());
                }else if(utility.getFacility().equals(bar)){
                    util.setBar(utility.getOnOff());
                }else if(utility.getFacility().equals(card_payment)){
                    util.setCard_payment(utility.getOnOff());
                }else if(utility.getFacility().equals(cctv_camera)){
                    util.setCctv_camera(utility.getOnOff());
                }else if(utility.getFacility().equals(conference_room)){
                    util.setConference_room(utility.getOnOff());
                }else if(utility.getFacility().equals(dinning_area)){
                    util.setDinning_area(utility.getOnOff());
                }else if(utility.getFacility().equals(elevator)){
                    util.setElevator(utility.getOnOff());
                }else if(utility.getFacility().equals(free_wifi)){
                    util.setFree_wifi(utility.getOnOff());
                }else if(utility.getFacility().equals(gym)){
                    util.setGym(utility.getOnOff());
                }else if(utility.getFacility().equals(geyser)){
                    util.setGeyser(utility.getOnOff());
                }else if(utility.getFacility().equals(hair_dryer)){
                    util.setHair_dryer(utility.getOnOff());
                }else if(utility.getFacility().equals(laundry)){
                    util.setLaundry(utility.getOnOff());
                }else if(utility.getFacility().equals(laptopFriendly)){
                    util.setLaptop_friendly(utility.getOnOff());
                }else if(utility.getFacility().equals(mini_fridge)){
                    util.setMini_fridge(utility.getOnOff());
                }else if(utility.getFacility().equals(nonAc)){
                    util.setNon_ac(utility.getOnOff());
                }else if(utility.getFacility().equals(parking_facility)){
                    util.setParking_facility(utility.getOnOff());
                }else if(utility.getFacility().equals(power_backup)){
                    util.setPower_backup(utility.getOnOff());
                }else if(utility.getFacility().equals(pre_book_meal)){
                    util.setPre_book_meal(utility.getOnOff());
                }else if(utility.getFacility().equals(pay_at_hotel)){
                    util.setPay_at_hotel(utility.getOnOff());
                }else if(utility.getFacility().equals(pet_friendly)){
                    util.setPet_friendly(utility.getOnOff());
                }else if(utility.getFacility().equals(room_heater)){
                    util.setRoom_heater(utility.getOnOff());
                }else if(utility.getFacility().equals(swimming_pool)){
                    util.setSwimming_pool(utility.getOnOff());
                }else if(utility.getFacility().equals(tv)){
                    util.setTv(utility.getOnOff());
                }else if(utility.getFacility().equals(wheelChair)){
                    util.setWheelchair(utility.getOnOff());
                }
            }
        }
    }
}
