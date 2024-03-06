package et.com.gebeya.emailservice.util;

public class MessageTemplate {
    private MessageTemplate(){
    }
    public static String addAccountMessage(String ownerName,String username,String password){
        return "Hello "+ ownerName+ "\nThank you for Joining Order-optima.\nYour Account is\n\nUsername: "+username+"\nPassword: "+password+"\n\n";
    }
    public static String forgotPasswordMessage(String otp){
        return otp+ "This is your Otp for resetting password, if you didn't request this you can simply ignore the message";
    }
//    public static String updateAccountMessage(String name){
//        return "Hello "+name+"\nYour account successfully updated";
//    }
//    public static String paymentPendingMessage(String name,Double price,String month){
//        return "Hello "+name+ "'s guardian,\nYour "+month+"'s tuition fee is "+price+".\nThe due date is after 10 days so make sure you pay on the given time.\nThank you.";
//    }
//    public static String paymentSuccessfulMessage(String name,String month){
//        return "Hello "+name+ "'s guardian,\nYour "+month+"'s tuition fee have been paid successfully.\nThank you.";
//    }

}
