package webservices.classes;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe client
 */

@XmlRootElement
public class Customer {

    private static int cpt = 0;

    private String refCustomer;         /* debut_nom + debut_prenom + cpt */
    private String nickname;
    private String name;
    private String email;

    public Customer() {
    }

    public Customer(String nickname, String name, String email)
    {
        cpt++;
        String subNickname, subName;
        subNickname = (nickname.length() > 4) ? nickname.substring(0, 4) : nickname;
        subName = (name.length() > 4) ? name.substring(0, 4) : name;
        this.refCustomer = subNickname + subName + String.valueOf(cpt);
        this.nickname = nickname;
        this.name = name;
        this.email = email;
    }

    /* GETTERS */
    public String getRefCustomer() {
        return refCustomer;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getNickname() {
        return nickname;
    }

    /* SETTERS */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRefCustomer(String refCustomer) {
        this.refCustomer = refCustomer;
    }

    /* AFFICHAGE */
    public String toString() {
        return refCustomer + " " + nickname + " " + name + " " + email + " ";
    }
}
