package csecarnival.uapadventurers.dto;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntity {
	

	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedDate;

	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	@PrePersist
	public void onPrePersist(){
		this.lastModifiedDate = new Date();
	}
	
	@PreUpdate
	public void onPreUpdate(){
		this.lastModifiedDate = new Date();
	}
}
