package com.longbig.multifunction.model.entity;

public class CityWithBLOBs extends City {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.shape
     *
     * @mbg.generated
     */
    private String shape;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.geom
     *
     * @mbg.generated
     */
    private byte[] geom;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.shape
     *
     * @return the value of city.shape
     *
     * @mbg.generated
     */
    public String getShape() {
        return shape;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.shape
     *
     * @param shape the value for city.shape
     *
     * @mbg.generated
     */
    public void setShape(String shape) {
        this.shape = shape == null ? null : shape.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.geom
     *
     * @return the value of city.geom
     *
     * @mbg.generated
     */
    public byte[] getGeom() {
        return geom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.geom
     *
     * @param geom the value for city.geom
     *
     * @mbg.generated
     */
    public void setGeom(byte[] geom) {
        this.geom = geom;
    }
}