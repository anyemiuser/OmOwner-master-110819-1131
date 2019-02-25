package com.anyemi.omrooms.payment.apkkit;

public interface WisePadControllerListener 
{
	public void onError(String error, int errorCode);
	public void onMswipeAppInstalled();
	public void onMswipeAppUpdated();
}