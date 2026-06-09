package br.com.siam.model;

public class GasStation {

    private Integer id;

    private String cnpj;

    private String corporateName;

    private String address;

    private Boolean active;

    public GasStation() {
    }

    public GasStation(
            Integer id,
            String cnpj,
            String corporateName,
            String address,
            Boolean active
    ) {
        this.id = id;
        this.cnpj = cnpj;
        this.corporateName = corporateName;
        this.address = address;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {return active;}

    public void setActive(Boolean active) {this.active = active;}

    @Override
    public String toString() {
        return corporateName;
    }
}