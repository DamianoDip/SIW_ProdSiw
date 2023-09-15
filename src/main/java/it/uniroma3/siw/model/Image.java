package it.uniroma3.siw.model;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Long id;

    private byte[] bytes;

    public Image(byte[] bytes) {
        this.setBytes(bytes);
    }
    
    public Image ( ){
        
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Long getId() {
        return id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + Arrays.hashCode(bytes);
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
        Image other = (Image) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (!Arrays.equals(bytes, other.bytes))
            return false;
        return true;
    }



    

    
}
