//
// Sobel Edge Detection Filter using CUDA
//
#include "opencv2/imgproc/imgproc.hpp"
#include <opencv2/highgui.hpp>
#include <iostream>
#include <string>
#include <stdio.h>
#include <cuda.h>
#include "cuda_runtime.h"

using namespace std;


//extern "C" bool sobelFilter_GPU_wrapper(const cv::Mat& input, cv::Mat& output);
extern "C" bool sobelFilter_CPU(const cv::Mat& input, cv::Mat& output);

// Run Sobel Edge Detect Filter on GPU
__global__ void sobelFilter(unsigned char *srcImage, unsigned char *dstImage, unsigned int width, unsigned int height)
{
   int x = blockIdx.x*blockDim.x + threadIdx.x;
   int y = blockIdx.y*blockDim.y + threadIdx.y;

   float Kx[3][3] = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
   float Ky[3][3] = {1, 2, 1, 0, 0, 0, -1, -2, -1};

   // only threads inside image will write results
   if((x>=FILTER_WIDTH/2) && (x<(width-FILTER_WIDTH/2)) && (y>=FILTER_HEIGHT/2) && (y<(height-FILTER_HEIGHT/2)))
   {
         // Gradient in x-direction 
         float Gx = 0;
         // Loop inside the filter to average pixel values
         for(int ky=-FILTER_HEIGHT/2; ky<=FILTER_HEIGHT/2; ky++) {
            for(int kx=-FILTER_WIDTH/2; kx<=FILTER_WIDTH/2; kx++) {
               float fl = srcImage[((y+ky)*width + (x+kx))];
               Gx += fl*Kx[ky+FILTER_HEIGHT/2][kx+FILTER_WIDTH/2];
            }
         }
         float Gx_abs = Gx < 0 ? -Gx : Gx;

         // Gradient in y-direction 
         float Gy = 0;
         // Loop inside the filter to average pixel values
         for(int ky=-FILTER_HEIGHT/2; ky<=FILTER_HEIGHT/2; ky++) {
            for(int kx=-FILTER_WIDTH/2; kx<=FILTER_WIDTH/2; kx++) {
               float fl = srcImage[((y+ky)*width + (x+kx))];
               Gy += fl*Ky[ky+FILTER_HEIGHT/2][kx+FILTER_WIDTH/2];
            }
         }
         float Gy_abs = Gy < 0 ? -Gy : Gy;

         dstImage[(y*width+x)] =  Gx_abs + Gy_abs;
   }
}


// The wrapper is use to call sobel edge detection filter 
extern "C" void sobelFilter_GPU_wrapper(const cv::Mat& input, cv::Mat& output)
{
        // Use cuda event to catch time
        cudaEvent_t start, stop;
        cudaEventCreate(&start);
        cudaEventCreate(&stop);

        // Calculate number of input & output bytes in each block
        const int inputSize = input.cols * input.rows;
        const int outputSize = output.cols * output.rows;
        unsigned char *d_input, *d_output;
        
        // Allocate device memory
        cudaMalloc<unsigned char>(&d_input,inputSize);
        cudaMalloc<unsigned char>(&d_output,outputSize);

        // Copy data from OpenCV input image to device memory
        cudaMemcpy(d_input,input.ptr(),inputSize,cudaMemcpyHostToDevice);

        // Specify block size
        const dim3 block(BLOCK_SIZE,BLOCK_SIZE);

        // Calculate grid size to cover the whole image
        const dim3 grid((output.cols + block.x - 1)/block.x, (output.rows + block.y - 1)/block.y);

        // Start time
        cudaEventRecord(start);

        // Run Sobel Edge Detection Filter kernel on CUDA 
        sobelFilter<<<grid,block>>>(d_input, d_output, output.cols, output.rows);

        // Stop time
        cudaEventRecord(stop);

        //Copy data from device memory to output image
        cudaMemcpy(output.ptr(),d_output,outputSize,cudaMemcpyDeviceToHost);

        //Free the device memory
        cudaFree(d_input);
        cudaFree(d_output);

        cudaEventSynchronize(stop);
        float milliseconds = 0;
        
        // Calculate elapsed time in milisecond  
        cudaEventElapsedTime(&milliseconds, start, stop);
        cout<< "\nProcessing time on GPU (ms): " << milliseconds << "\n";
}


// Program main
int main( int argc, char** argv ) {

   // name of image
   string image_name = "sample";

   // input & output file names
   string input_file =  image_name+".jpeg";
   string output_file_cpu = image_name+"_cpu.jpeg";
   string output_file_gpu = image_name+"_gpu.jpeg";

   // Read input image 
   cv::Mat srcImage = cv::imread(input_file ,CV_LOAD_IMAGE_UNCHANGED);
   if(srcImage.empty())
   {
      std::cout<<"Image Not Found: "<< input_file << std::endl;
      return -1;
   }
   cout <<"\ninput image size: "<<srcImage.cols<<" "<<srcImage.rows<<" "<<srcImage.channels()<<"\n";

   // convert RGB to gray scale
   cv::cvtColor(srcImage, srcImage, CV_BGR2GRAY);
  
   // Declare the output image  
   cv::Mat dstImage (srcImage.size(), srcImage.type());

   // run sobel edge detection filter on GPU  
   sobelFilter_GPU_wrapper(srcImage, dstImage);
   // normalization to 0-255
   dstImage.convertTo(dstImage, CV_32F, 1.0 / 255, 0);
   dstImage*=255;
   // Output image
   imwrite(output_file_gpu, dstImage);

   // run sobel edge detection filter on CPU  
   sobelFilter_CPU(srcImage, dstImage);
   // normalization to 0-255
   dstImage.convertTo(dstImage, CV_32F, 1.0 / 255, 0);
   dstImage*=255;
   // Output image
   imwrite(output_file_cpu, dstImage);
      
   return 0;
}




