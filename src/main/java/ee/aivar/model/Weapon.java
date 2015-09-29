package ee.aivar.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Time;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Weapon implements Serializable {

    @Id
	@NotNull
    private String id;

	@NotNull
	private String weapon_rack_id;

	@NotNull
	@Size(min = 1, max = 25)
//    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String name;

	@NotNull
	private String caliber;

	@NotNull
	private int weight;

    @NotNull
//	@Temporal(TemporalType.TIMESTAMP)
	private Time put_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWeapon_rack_id() {
		return weapon_rack_id;
	}

	public void setWeapon_rack_id(String weapon_rack_id) {
		this.weapon_rack_id = weapon_rack_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaliber() {
		return caliber;
	}

	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Time getPut_time() {
		return put_time;
	}

	public void setPut_time(Time put_time) {
		this.put_time = put_time;
	}
}
