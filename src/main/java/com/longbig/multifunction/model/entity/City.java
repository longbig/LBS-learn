package com.longbig.multifunction.model.entity;

public class City {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.adcode
     *
     * @mbg.generated
     */
    private Integer adcode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.id
     *
     * @return the value of city.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.id
     *
     * @param id the value for city.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.name
     *
     * @return the value of city.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.name
     *
     * @param name the value for city.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.adcode
     *
     * @return the value of city.adcode
     *
     * @mbg.generated
     */
    public Integer getAdcode() {
        return adcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.adcode
     *
     * @param adcode the value for city.adcode
     *
     * @mbg.generated
     */
    public void setAdcode(Integer adcode) {
        this.adcode = adcode;
    }
}