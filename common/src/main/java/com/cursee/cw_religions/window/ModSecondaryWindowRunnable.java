package com.cursee.cw_religions.window;

import com.cursee.cw_religions.Constants;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBEasyFont;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModSecondaryWindowRunnable implements Runnable {

    public static long window = 0L;
    public static ModSecondaryWindowRunnable instance;
    private static final List<String> logLines = new ArrayList<>();

    private int windowWidth = 800;
    private int windowHeight = 600;

    private float rotation = 0.0f;
    private long lastLogTime = 0;

    private static volatile boolean shouldExit = false;

    public ModSecondaryWindowRunnable() {
        if (instance == null) instance = this;
    }

    @Override
    public void run() {
        if (instance == null) instance = this;

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            System.err.println("Failed to initialize GLFW");
            return;
        }

        // Window setup
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        window = GLFW.glfwCreateWindow(windowWidth, windowHeight, "CW: Religions Debug", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            System.err.println("Failed to create GLFW window");
            return;
        }

        GLFW.glfwSetWindowSizeCallback(window, (window, width, height) -> {
            windowWidth = width;
            windowHeight = height;
            GL11.glViewport(0, 0, width, height);
        });

        GLFW.glfwSetWindowPos(window, 100, 100);
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GL.createCapabilities();
        GLFW.glfwShowWindow(window);

        // Initial GL state
        GL11.glClearColor(0f, 0f, 0f, 1f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // GLFW.glfwInit() -> "Additional calls to this function after successful initialization but before termination will return TRUE immediately."
        while (GLFW.glfwInit() && !shouldExit && !GLFW.glfwWindowShouldClose(window)) {
            // updateLog();
            render();
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        GLFW.glfwDestroyWindow(window);
        // GLFW.glfwTerminate();

        instance = null;
    }

    private void render() {

        // Always re-fetch size to keep windowWidth/windowHeight accurate
        int[] widthArr = new int[1];
        int[] heightArr = new int[1];
        GLFW.glfwGetWindowSize(window, widthArr, heightArr);
        windowWidth = widthArr[0];
        windowHeight = heightArr[0];

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // 3D mode
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float aspect = (float) windowWidth / (float) windowHeight;
        GL11.glFrustum(-aspect, aspect, -1.0, 1.0, 1.5, 50.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // Render cube
        GL11.glTranslatef(0f, 0f, -5f);
        GL11.glRotatef(rotation, 1f, 1f, 0f);
        rotation += 0.5f;
        drawCube();

        // 2D mode for text
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, windowWidth, windowHeight, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        renderText();
    }

    private void drawCube() {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glColor3f(0.1f, 0f, 0f); // Front
        GL11.glVertex3f(-1, -1, 1);
        GL11.glVertex3f(1, -1, 1);
        GL11.glVertex3f(1, 1, 1);
        GL11.glVertex3f(-1, 1, 1);

        GL11.glColor3f(0f, 0.1f, 0f); // Back
        GL11.glVertex3f(-1, -1, -1);
        GL11.glVertex3f(-1, 1, -1);
        GL11.glVertex3f(1, 1, -1);
        GL11.glVertex3f(1, -1, -1);

        GL11.glColor3f(0f, 0f, 0.1f); // Left
        GL11.glVertex3f(-1, -1, -1);
        GL11.glVertex3f(-1, -1, 1);
        GL11.glVertex3f(-1, 1, 1);
        GL11.glVertex3f(-1, 1, -1);

        GL11.glColor3f(0.1f, 0.1f, 0f); // Right
        GL11.glVertex3f(1, -1, -1);
        GL11.glVertex3f(1, 1, -1);
        GL11.glVertex3f(1, 1, 1);
        GL11.glVertex3f(1, -1, 1);

        GL11.glColor3f(0f, 0.1f, 0.1f); // Top
        GL11.glVertex3f(-1, 1, -1);
        GL11.glVertex3f(-1, 1, 1);
        GL11.glVertex3f(1, 1, 1);
        GL11.glVertex3f(1, 1, -1);

        GL11.glColor3f(0.1f, 0f, 0.1f); // Bottom
        GL11.glVertex3f(-1, -1, -1);
        GL11.glVertex3f(1, -1, -1);
        GL11.glVertex3f(1, -1, 1);
        GL11.glVertex3f(-1, -1, 1);

        GL11.glEnd();
    }

    private void renderText() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor3f(1f, 1f, 1f); // white

        float scale = windowHeight / 600.0f;
        int y = (int) (10 * scale);

//        for (String logLine : logLines) {
        for (int i = logLines.size() - 1; i >= 0; i--) {
            String logLine = logLines.get(i);
            List<String> wrappedLines = wrapTextToWidth(logLine, (int)((windowWidth - 20) / scale));

            for (String subLine : wrappedLines) {
                ByteBuffer buffer = MemoryUtil.memAlloc(99999);

                GL11.glPushMatrix();
                GL11.glTranslatef(10, y, 0);
                GL11.glScalef(scale, scale, 1.0f);
                GL11.glTranslatef(-10, -y, 0);

                int quads = STBEasyFont.stb_easy_font_print(10, y, subLine, null, buffer);
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glVertexPointer(2, GL11.GL_FLOAT, 16, buffer);
                GL11.glDrawArrays(GL11.GL_QUADS, 0, quads * 4);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

                GL11.glPopMatrix();
                MemoryUtil.memFree(buffer);

                y += (int) (17 * scale); // 13px font height + padding
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private List<String> wrapTextToWidth(String text, int maxWidthPixels) {
        int maxCharsPerLine = maxWidthPixels / 8; // STBEasyFont uses ~8px/char
        List<String> result = new ArrayList<>();

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 > maxCharsPerLine) {
                result.add(line.toString());
                line = new StringBuilder(word);
            } else {
                if (line.length() > 0) line.append(" ");
                line.append(word);
            }
        }

        if (!line.isEmpty()) {
            result.add(line.toString());
        }

        return result;
    }

    public void log(String message) {
        if (logLines.size() > 20) logLines.remove(0);
        Constants.LOG.info(message);
        logLines.add(message);
    }

    public static void shutdown() {

        shouldExit = true;

        if (window != 0L) {
            GLFW.glfwSetWindowShouldClose(window, true);
            GLFW.glfwDestroyWindow(window);
            window = 0L;
            instance = null;
            // GLFW.glfwTerminate();
            // window = 0L;
            // Minecraft.getInstance().close();
        }
    }
}
