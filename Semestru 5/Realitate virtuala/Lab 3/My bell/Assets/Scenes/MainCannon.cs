using System;
using System.Collections;
using System.Collections.Generic;
using System.Security.Cryptography.X509Certificates;
using UnityEngine;

public class MainCannon : MonoBehaviour
{
    private Camera mainCamera;
    private bool shotCalled = false;
    //private bool pressDown = false;

    public GameObject cannonBallDefault;
    public Transform firePoint;

    private float rotateUpDown, rotateUpDownSpeed;
    private float rotateLeftRight, rotateLeftRightSpeed;
    
    private Vector3 origin = new Vector3(0f, 0f, 0f);

    private Vector3 initialVelocity;
    
    public float maxUpRotation = 90f;
    public float maxDownRotation = 0f;
    public float maxLeftRotation = 70f;
    public float maxRightRotation = 110f;

    private float timePressed;
    private float maxPressSeconds = 0.5f;
    private float shotPower;
    public float defaultPower = 10f;
    
    void Start()
    {
        mainCamera = Camera.main;
        rotateUpDownSpeed = 100f;
        rotateLeftRightSpeed = 100f;
    }

    // Update is called once per frame
    void Update()
    {
        /*pressUp = false;
        if (Input.GetKey(KeyCode.UpArrow))
        {
            pressUp = true;
        }

        if (pressUp)
        {
            Vector3 mousePosition = mainCamera.ScreenToWorldPoint(Input.mousePosition);
            mousePosition.z = 0;
            
            
            transform.LookAt(mousePosition);
        }*/
        rotateUpDown = -1.0f * Input.GetAxis("Vertical") * rotateUpDownSpeed * Time.deltaTime;
        rotateLeftRight = 1.0f * Input.GetAxis("Horizontal") * rotateLeftRightSpeed * Time.deltaTime;
        
        /*if (Input.GetKey(KeyCode.Space))
        {
            shotCalled = true;
        }*/
        
        if (Input.GetKeyDown(KeyCode.Space))
        {
            timePressed = Time.time;
        }
        if (Input.GetKeyUp(KeyCode.Space))
        {
            timePressed = Time.time - timePressed;

            if (timePressed > maxPressSeconds)
            {
                timePressed = maxPressSeconds;
            }

            shotPower = timePressed / maxPressSeconds;
            initialVelocity = (firePoint.position - origin) * (defaultPower * shotPower);
            
            shotCalled = true;
        }
        
        if (shotCalled)
        {
            Shoot();
        }

        shotCalled = false;
    }

    private void LateUpdate()
    {
        transform.RotateAround(origin, Vector3.up, rotateLeftRight);
        transform.Rotate(rotateUpDown, 0f, 0f);
    }

    private void Shoot()
    {
        GameObject cannonBall = Instantiate(cannonBallDefault, firePoint.position, Quaternion.identity);
        Rigidbody rb = cannonBall.GetComponent<Rigidbody>();
        rb.AddForce(initialVelocity, ForceMode.Impulse);
    }
}
