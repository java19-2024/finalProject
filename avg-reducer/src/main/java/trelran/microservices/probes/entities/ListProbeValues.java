package trelran.microservices.probes.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;

@RedisHash
@Getter
public class ListProbeValues {
	long id;
	List<Integer>values = new ArrayList<>();
	
	public ListProbeValues(long id) {
		super();
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListProbeValues other = (ListProbeValues) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
