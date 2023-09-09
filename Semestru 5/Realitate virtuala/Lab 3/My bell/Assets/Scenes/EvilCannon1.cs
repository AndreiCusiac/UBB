using System;
using System.Collections;
using System.Collections.Generic;
using System.Security.Cryptography.X509Certificates;
using UnityEngine;

public class EvilCannon1 : MonoBehaviour
{
    private Camera mainCamera;
    private bool shotCalled = false;
    //private bool pressDown = false;

    public GameObject cannonBallDefault;
    public Transform firePoint;

    private float rotateUpDown, rotateUpDownSpeed;
    //private float rotateLeftRight, rotateLeftRightSpeed;
    private float rotateUp = -1f;
    
    //private Vector3 origin = new Vector3(0f, 0f, 0f);
    public Transform origin;

    private Vector3 initialVelocity;
    
    public float maxUpRotation = -30f;
    public float maxDownRotation = -20f;
    public float maxLeftRotation = 0f;
    public float maxRightRotation = 0f;
    private float angle;
    private float delta_angle;

    private float nextShot = 0f;
    //private float maxPressSeconds = 0.5f;
    public float shotInterval = 2f;
    private float shotPower = 1f;
    public float defaultPower = 10f;

    void Start()
    {
        angle = float.Parse(maxDownRotation.ToString());
        mainCamera = Camera.main;
        rotateUpDownSpeed = 100f;
        //rotateLeftRightSpeed = 100f;
    }

    // Update is called once per frame
    void Update()
    {
        float dt = Time.deltaTime; //seconds
        delta_angle = rotateUpDownSpeed * dt;
        angle += delta_angle;
        if (angle < maxUpRotation || angle > maxDownRotation)
        {
            rotateUpDownSpeed *= -1.0f;
            delta_angle *= -1.0f;
            angle += 2 * delta_angle;
        }
        
        //rotateUpDown = rotateUp * rotateUpDownSpeed * Time.deltaTime;
        //rotateLeftRight = 1.0f * Input.GetAxis("Horizontal") * rotateLeftRightSpeed * Time.deltaTime;

        if (Time.time > nextShot)
        {
            initialVelocity = (firePoint.position - origin.position) * (defaultPower * shotPower);
            
            shotCalled = true;
            nextShot += shotInterval;
        }

        if (shotCalled)
        {
            Shoot();
        }

        shotCalled = false;
    }

    private void LateUpdate()
    {
        //transform.RotateAround(origin.position, Vector3.up, rotateLeftRight);
        //transform.Rotate(rotateUpDown, 0f, 0f);
        //transform.Rotate(delta_angle, 0f, 0f);
        //Debug.Log("transform: " + transform.eulerAngles.x);
        if (rotateUpDown < maxUpRotation || rotateUpDown > maxDownRotation)
        {
            rotateUp *= -1;
        }
    }

    private void Shoot()
    {
        GameObject cannonBall = Instantiate(cannonBallDefault, firePoint.position, Quaternion.identity);
        Rigidbody rb = cannonBall.GetComponent<Rigidbody>();
        rb.AddForce(initialVelocity, ForceMode.Impulse);
    }
}
