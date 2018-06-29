package com.example.matah.sligro_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Emil Karamihov on 12-Dec-17.
 */

public class CustomAdapter extends BaseAdapter {
    private ArrayList<Guide> guides;
    private Context mContext;
    private Integer[] img = {
        R.drawable.ic_hacker_news,
        R.drawable.ic_menu_camera,
        R.drawable.ic_guide_book,
        R.drawable.ic_hacker_news,
    };

    public CustomAdapter(Context c) {
        mContext = c;
        guides = new ArrayList<>();
    }

    public int getCount() {
        return img.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_single,null);
        }
        // Add Guides
        populateList();
        //get
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.picture);
        //set
        textView.setText(guides.get(position).getTitle());
        imageView.setImageResource(guides.get(position).getImg());
        return convertView;
    }

    public ArrayList<Guide> getList() {
        return this.guides;
    }

    private void populateList() {
        guides.add(new Guide("Phishing E-mail",img[0],"Phishing mails are almost always sent with the purpose of luring confidential credit card or bank information out of their victims. There may also be talk about phishing where the attacker accesses your personal information for the purpose of identity theft. It frequently happens that individuals receive emails from unknown senders who seem to be from their bank or public institutions such as the tax authorities. When the victim follows a link in the mail they are taken to a website which looks official. Sometimes the only difference is a minor spelling mistake in the website address which the victim does not notice.\n" +
                "\n" +
                "Phishing can take place if you enter your confidential banking information on a fake website from which the attackers can use the information to transfer money from yours to their bank account. Another method of phishing is that the victim follows a link on the fake website. When the user clicks the link an application will automatically be installed on their computer. This application can monitor their activities and thus snap up confidential information.\n" +
                "\n" +
                "Since scammers began phishing with the use of emails, new techniques to gain access to users banking and credit card information have emerged. Smishing is a similar phenomenon related to SMS text messages. Here, it may be a text which appears to be from a public authority, or  premium rate text where the receiver is tricked into entering his or her credit card information to receive a particular item or participate in a competition. Finally, there is also real-time phishing where the victim sees a popup message requesting information such as a code from their key card for online banking services. With this key, fraudsters can then access online banking facilities."));
        guides.add(new Guide("Trojans",img[1],"Trojans are computer programs which are disguised as harmless programs or files. Trojans can delete your data, send copies of themselves to email addresses in your address book and take control of your computer. Since they can send copies of themselves to all your contacts, trojans spread with the speed of lightning and are incredibly hard to stop. They can also be used to make your computer attack other computers. Trojan horses come into your computer when downloaded from the internet or you open an attachment in your email. The attached files can be text documents, images, video, audio, or applications.\n" +
                "\n" +
                "It is important to understand that trojans can only be installed on your computer if you make an active action. That is why, in connection with this type of attack, we are talking about social engineering where the trojan horse convinces the victim that he or she needs to do something specific like to follow a link or open a file. It is also important to understand that trojan horses can spread virtually all types of viruses, malware, ransomware and spyware.\n" +
                "\n" +
                "Although we have known about trojans since the 1990s, they are still going strong. Today hackers still use the traditional method of infected attachments in mails but they have also found new media where they can trick users. You may install a trojan horse by downloading a picture from a Facebook message or text message on your phone. Here it is also the case that they can send themselves to all your Facebook friends or to anyone in your phonebook. Since most of our devices today communicate with each other, trojans can spread across all devices.\n" +
                "\n" +
                "Ransomware attacks are often spread using a Trojan horse. In our article on ransomware, we explain what the term covers, and how you can protect yourself against this type of attack."));
        guides.add(new Guide("Worms",img[2],"Unlike trojan horses and phishing mails worms can spread viruses and reproduce themselves without you doing anything other than being online. You do not have to download infected files or visit fake websites to become a victim of a computer worm. A computer worm is not a virus because it does not need to connect with another program to spread. The worm often brings one or more malicious programs such as viruses and trojans.\n" +
                "\n" +
                "Worms exploit vulnerabilities in networks. The typical computer worm spreads itself by spamming copies of itself into random IP addresses. IP stands for Internet Protocol and all devices that communicate with each other over the Internet have an IP address. In order for the communication not to get mixed up, IP addresses identify an internet connection with a network device. Each copy of the computer worm has been instructed to attack a certain type of vulnerability in a particular network. When a randomly hit device has a network connection with the specific vulnerability, the worm uses this uncertainty to access the computer and make its attack. Once that happens the worm uses the device it has just infected to spam multiple random IP addresses, thus restarting the process again.\n" +
                "\n" +
                "Computer worms can thus spread themselves to a vast amount of computers in almost no time and there is almost always no explanation of who has been attacked. You can reduce the risk of computer worms by always having your operating system up to date and using a firewall on your computer."));
        guides.add(new Guide("Guide to a secure computer",img[3],"Install antimalware and antispyware programs. Even the most careful and attentive computer user can become a victim of spyware, trojan horses, phishing and, not least, computer worms. Anti Explotator is an anti-spyware program that secures you and your computer against malware. Anti Explotator protects you from keyloggers, rootkits, adware, spam, toolbars, malware and many more threats found on the Internet."));
    }
    // references to our images

}
