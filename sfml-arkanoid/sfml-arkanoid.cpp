#include <SFML/Window.hpp>
#include <SFML/Graphics.hpp>
using namespace std;
using namespace sf;

// constexpr defines an inmutable compile-time value
constexpr int windowWidth{800}, windowHeight{600};
constexpr float ballRadius{10.f}, ballVelocity{8.f};
constexpr float vausWidth{60.f}, vausHeight{20.f}, vausVelocity{6.f};


// all implicitly public
struct Ball
{
    // SFML class to define a renderable circle
    CircleShape shape;
    // 2D vector for the Ball's velocity -> top left of the screen
    Vector2f velocity{-ballVelocity, -ballVelocity};
    
    // mx/my staring x/y coordinate
    Ball(float mx, float my) {
        shape.setPosition(mx, my);
        shape.setRadius(ballRadius);
        shape.setFillColor(Color::Red);
        shape.setOrigin(ballRadius, ballRadius);
    }

    // Move the ball's shape by the current velocity
    void update() {
        shape.move(velocity);
        // ------> x
        // |
        // |  o        Keep the ball inside the screen
        // v
        // Check left and right bounds -> whether we are out
        if (left() < 0)
            velocity.x = ballVelocity;
        else if (right() > windowWidth)
            velocity.x = -ballVelocity;
        // Check top and bottom bounds -> again, whether we are out
        if (top() < 0)
            velocity.y = ballVelocity;
        else if (bottom() > windowHeight)
            velocity.y = -ballVelocity;
    }

    float x() { return shape.getPosition().x; }
    float y() { return shape.getPosition().y; }
    float left() { return x() - shape.getRadius(); }
    float right() { return x() + shape.getRadius(); }
    float top() { return y() - shape.getRadius(); }
    float bottom() { return y() + shape.getRadius(); }
};

struct Vaus
{
    RectangleShape shape;
    Vector2f velocity;

    Vaus(float mx, float my) {
        shape.setPosition(mx, my);
        shape.setSize({vausWidth, vausHeight});
        shape.setFillColor(Color::Red);
        shape.setOrigin(vausWidth / 2.f, vausHeight / 2.f);
    }

    void update() {
        shape.move(velocity);
        // Check if left & rigth keys are pressed to move Vaus, keep Vaus
        // inside the window
        if (Keyboard::isKeyPressed(Keyboard::Key::Left) && left() > 0)
            velocity.x = -vausVelocity;
        else if (Keyboard::isKeyPressed(Keyboard::Key::Right)
                 && right() < windowWidth)
            velocity.x = vausVelocity;
        else
            velocity.x = 0;
    }

    float x() { return shape.getPosition().x; }
    float y() { return shape.getPosition().y; }
    float left() { return x() - shape.getSize().x / 2.f; }
    float right() { return x() + shape.getSize().x / 2.f; }
    float top() { return y() - shape.getSize().y / 2.f; }
    float bottom() { return y() + shape.getSize().y / 2.f; }
};

// Generic function to check if two shapes are intersecting
template<class T1, class T2> bool isIntersecting(T1 &ma, T2 &mb)
{
    return ma.right() >= mb.left() && ma.left() <= mb.right()
        && ma.bottom() >= mb.top() && ma.top() <= mb.bottom();
}

void testCollision(Vaus &mvaus, Ball &mball)
{
    if (!isIntersecting(mvaus, mball))
        return;
    // if is intersecting push the ball upwards
    mball.velocity.y = -ballVelocity;
    if (mball.x() < mvaus.x())
        mball.velocity.x = -ballVelocity;
    else
        mball.velocity.x = ballVelocity;
}

int main()
{
    Vaus vaus{windowWidth /2, windowHeight - 50};
    
    Ball ball{windowWidth / 2, windowHeight / 2};
    
    // Game window
    RenderWindow window{{windowWidth, windowHeight}, "SFML - Arkanoid"};
    window.setFramerateLimit(60);

    // Game loop
    while (true) {
        window.clear(Color::Black);
        
        if (Keyboard::isKeyPressed(Keyboard::Key::Escape))
            break;

        // update the ball & vaus
        ball.update();
        vaus.update();
        // test collisions
        testCollision(vaus, ball);
        // draw ball & vaus
        window.draw(ball.shape);
        window.draw(vaus.shape);
        window.display();
    }
    return 0;
}
