package xtorq;

import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public  class LevelEditorScene extends Scene {
    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +  // This line was changed
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 aColor;\n" +
            "\n" +
            "void main(){\n" +
            "    aColor = fColor;\n" +
            "}";

    private int vertexID, fragmentID,shaderProgram;

    // The correct coordinates for a centered square
    private float[] vertexArray = {
            // position              // color
            0.5f, -0.5f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f, // Bottom right
            -0.5f,  0.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f, // Top left
            0.5f,  0.5f, 0.0f,       0.0f, 0.0f, 1.0f, 1.0f, // Top right
            -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f  // Bottom left
    };
    // in counter-clockwise order

    private int[] elementArray={
            /*
                    *       *


                    *       *
             */
            2,1,0, //t r tri
            0,1,3//b l tri


    };
    private int vaoID, vboID,eboID;




    public LevelEditorScene() {

    }

    @Override
    public void init() {
        // compile and link shaders
        //load and compile vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        //pass shader source to gpu
        glShaderSource(vertexID,vertexShaderSrc);
        glCompileShader(vertexID);

        //to check error
        int success = glGetShaderi(vertexID,GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID,GL_INFO_LOG_LENGTH);
            System.out.println("Shader compilation error code: " + glGetShaderInfoLog(vertexID,len));
            assert false:"";
        }
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID,fragmentShaderSrc);
        glCompileShader(fragmentID);
        success = glGetShaderi(fragmentID,GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID,GL_INFO_LOG_LENGTH);
            System.out.println("Shader compilation error code: " + glGetShaderInfoLog(fragmentID,len));
            assert false:"";
        }
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram,vertexID);
        glAttachShader(shaderProgram,fragmentID);
        glLinkProgram(shaderProgram);
        success = glGetProgrami(shaderProgram,GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgram,GL_INFO_LOG_LENGTH);
            System.out.println("Shader compilation error code:"+ glGetProgramInfoLog(shaderProgram,len));
            assert false:"";
        }
//        // generate VAO VBO EBO BUffer objects and send to GPU
//        vaoID = glGenVertexArrays();
//        glBindVertexArray(vaoID);
//        // create float buffer
//        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
//        vertexBuffer.put(vertexArray).flip();
//
//        vboID = glGenBuffers();
//        glBindBuffer(GL_ARRAY_BUFFER, vboID);
//        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
//
//        //creating indices
//        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
//        elementBuffer.put(elementArray).flip();
//
//        eboID = glGenBuffers();
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
//        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
//
//        //add vertex attribute position
//        int positionSize =3;
//        int colorSize = 4;
//        int floatSize = 4;
//        int vertexSize = (positionSize + colorSize)*floatSize;
//        glVertexAttribPointer(0,positionSize,GL_FLOAT,false,vertexSize,0);
//        glEnableVertexAttribArray(0);
//
//        glVertexAttribPointer(1,colorSize,GL_FLOAT,false,vertexSize,positionSize*floatSize);
//        glEnableVertexAttribArray(1);
// ...inside the init() method, after the shader linking...

// ============================================================
// == GENERATE VAO, VBO, EBO AND SEND TO GPU
// ============================================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

// Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

// Create VBO and upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

// Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

// Create EBO and upload the element buffer
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

// ============================================================
// == SET UP VERTEX ATTRIBUTE POINTERS
// ============================================================
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;

// Position attribute
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

// Color attribute
        long colorOffsetBytes = (long)positionSize * floatSizeBytes;
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, colorOffsetBytes);
        glEnableVertexAttribArray(1);


    }

    @Override
    public void update(float dt) {
        //bind shader program
        glUseProgram(shaderProgram);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT,0);

        //unbinding
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glUseProgram(0);
    }
}
