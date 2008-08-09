/*
 * $Id: XMLTrianglePolygon.java,v 1.15 2008/02/13 08:05:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.model;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.xml.bind.annotation.*;

public class XMLTrianglePolygon {
	@XmlElement
	private Location[] _point = new Location[3];
	@XmlAttribute
	private String _color;
	@XmlElement
	private Location _location;
	@XmlElement
    private Rotation _rotation;
	
	private Vector3f[] _normal = new Vector3f[3]; 
	
	private Matrix4f _matrix;
	
	
	
	/**
     * Field _transparent
     */
    protected boolean _transparent;

    /**
     * keeps track of state for field: _transparent
     */
    private boolean _has_transparent;

	public XMLTrianglePolygon(){
		for(int i=0; i<_point.length; i++){
			_point[i] = new Location();
		}
		_color = "orange";
		_matrix = new Matrix4f(
				1.0f,0.0f,0.0f,0.0f,
				0.0f,1.0f,0.0f,0.0f,
				0.0f,0.0f,1.0f,0.0f,
				0.0f,0.0f,0.0f,1.0f);
		setNormalVector();
	}
	
	public void setPointLocation(int pointNum, float x, float y, float z){
		_point[pointNum].setX(x);
		_point[pointNum].setY(y);
		_point[pointNum].setZ(z);
		setNormalVector();
	}
	
	public void setPointLocations(Location loc1, Location loc2, Location loc3){
		_point[0] =loc1;
		_point[1] =loc2;
		_point[2] =loc3;
		setNormalVector();
	}
	
	public void setPointLocations(Location[] point){
		this._point = point;
		setNormalVector();
	}
	
	public void setColor(String c){
		_color = c;
	}
	
	public void setLocation(Location loc){
		_location = loc;
	}
	
	public void setRotation(Rotation rot){
		_rotation = rot;
	}
	
	public void setNormalVector(Location loc){
		_normal[0] = new Vector3f(loc.loadX(),loc.loadY(),loc.loadZ());
		_normal[1] = new Vector3f(loc.loadX(),loc.loadY(),loc.loadZ());
		_normal[2] = new Vector3f(loc.loadX(),loc.loadY(),loc.loadZ());
	}
	
	public void setNormalVector(Vector3f[] nor){
		_normal = nor;
	}
	
	public void setNormalVector(){
		Vector3f v1 = new Vector3f(_point[1].loadX()-_point[0].loadX(),
				_point[1].loadY()-_point[0].loadY(),
				_point[1].loadZ()-_point[0].loadZ());
		Vector3f v2 = new Vector3f(_point[2].loadX()-_point[1].loadX(),
				_point[2].loadY()-_point[1].loadY(),
				_point[2].loadZ()-_point[1].loadZ());
		Vector3f n = new Vector3f();
		n.cross(v1,v2);
		n.normalize();
		_normal[0] = n;
		_normal[1] = n;
		_normal[2] = n;
	}
	
	public void setMatrix(Matrix4f matrix){
		_matrix = matrix;
	}
	
	public float loadPointLocationX(int pointNum){
		return _point[pointNum].loadX();
	}
	public float loadPointLocationY(int pointNum){
		return _point[pointNum].loadY();
	}
	public float loadPointLocationZ(int pointNum){
		return _point[pointNum].loadZ();
	}
	
	public String loadColor(){
		return _color;
	}
	
	public Location loadLocation(){
		return _location;
	}
	
	public Rotation loadRotation(){
		return _rotation;
	}
	
	public Vector3f[] loadNormalVector(){
		setNormalVector();
		return _normal;
	}
	
	public Matrix4f loadMatrix(){
		return _matrix;
	}
	
	/**
     * Method deleteTransparent
     */
    public void deleteTransparent()
    {
        this._has_transparent= false;
    } //-- void deleteTransparent() 
	
	/**
     * Sets the value of field 'transparent'.
     * 
     * @param transparent the value of field 'transparent'.
     */
    public void setTransparent(boolean transparent)
    {
        this._transparent = transparent;
        this._has_transparent = true;
    }
    
    /**
     * Returns the value of field 'transparent'.
     * 
     * @return the value of field 'transparent'.
     */
    public boolean loadTransparent()
    {
        return this._transparent;
    } //-- boolean getTransparent() 
    
    /**
     * Method hasTransparent
     * @return has_tranparent
     */
    public boolean hasTransparent()
    {
        return this._has_transparent;
    } //-- boolean hasTransparent() 
}
