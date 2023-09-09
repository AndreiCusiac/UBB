using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SnakeMovement : MonoBehaviour
{
    // Settings
    public float MoveSpeed = 5;
    public float SteerSpeed = 180;
    public float BodySpeed = 5;
    public bool toMove = false;
    public int Length = 20;
    
    public int Gap = 1;
    private bool toGrow = true;

    private float moveForwardBackward;
    private float steerDirection;

    public GameObject BodyPrefab;
    private List<GameObject> BodyParts = new List<GameObject>();
    
    private List<Vector3> PositionsHistory = new List<Vector3>();


    // References
    
    void Start() {
        /*for (int i = 0; i < Length; i++)
        {
            GrowSnake();
        }*/
    }
    // Update is called once per frame
    void Update() {

        if (Input.GetKeyDown(KeyCode.Space) && toGrow)
        {
            toMove = true;
            for (int i = 0; i < Length; i++)
            {
                GrowSnake();
            }

            toGrow = false;
        }
        
        if (toMove)
        {
            //moveForwardBackward = 1.0f * Input.GetAxis("Vertical") * MoveSpeed * Time.deltaTime;
            moveForwardBackward = 1.0f * Input.GetAxis("Fire2") * MoveSpeed * Time.deltaTime;
            //steerDirection = Input.GetAxis("Horizontal"); // Returns value -1, 0, or 1
            steerDirection = Input.GetAxis("Fire1"); // Returns value -1, 0, or 1

            //transform.position += -transform.right * MoveSpeed * Time.deltaTime;

            //transform.position
        }
    }

    private void LateUpdate()
    {
        if (toMove)
        {
            transform.position += -transform.right * moveForwardBackward;
            transform.Rotate(Vector3.up * steerDirection * SteerSpeed * Time.deltaTime);
        
            PositionsHistory.Insert(0, transform.position);

            if (moveForwardBackward != 0)
            {
                int index = 0;
                foreach (var body in BodyParts) {
                    //Vector3 point = PositionsHistory[Mathf.Min(index * Gap, PositionsHistory.Count - 1)];
                    Vector3 point = PositionsHistory[Mathf.Clamp(index * Gap, 0, PositionsHistory.Count - 1)];
                    Vector3 moveDirection = point - body.transform.position;

                    body.transform.position += moveDirection * BodySpeed * Time.deltaTime;
                    body.transform.LookAt(point);

                    index++;
                }
            }
        }
    }
    
    private void GrowSnake() {
        // Instantiate body instance and
        // add it to the list
        //GameObject body = Instantiate(BodyPrefab);
        GameObject body = Instantiate(BodyPrefab, transform.position, Quaternion.identity);

        BodyParts.Add(body);
    }
}
