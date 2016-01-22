import javax.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Integer num_reservation;
    private void setId(Integer i) { num_reservation = i; }
    public Integer getId()  { return num_reservation; }

    @ManyToOne
    @JoinColumn (name="num_client")
    private Client m_client;
    public void setClient (Client a) {m_client = a;}
    public Client getClient () {return m_client;}
    
    @ManyToOne
    @JoinColumn (name="num_vol")
    private Vol m_vol;
    public void setVol (Vol a) {m_vol = a;}
    public Client getVol () {return m_vol;}
}

select reservation0_.m_client as client_, reservation0_.m_vol as vol_,
reservation0_.m_hotel as hotel_
from Reservation reservation0_
where reservation0_.m_client=? And reservation0_.m_vol=? and eservation0_.m_hotel=?
