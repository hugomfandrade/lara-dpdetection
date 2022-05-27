

#include <cppunit/TestLeaf.h>
#include <cppunit/TestFixture.h>
#include <string>


class TestResult;


/*! \brief A single test object.
 *
 * This class is used to implement a simple test case: define a subclass
 * that overrides the runTest method.
 *
 * You don't usually need to use that class, but TestFixture and TestCaller instead.
 *
 * You are expected to subclass TestCase is you need to write a class similiar
 * to TestCaller.
 */
class TestCase : public TestLeaf,
                             public TestFixture
{
public:

    TestCase( const std::string &name );

    TestCase();

    ~TestCase() override;
    
    virtual void run(TestResult *result) override;

    std::string getName() const override;

    //! FIXME: this should probably be pure virtual.
    virtual void runTest();
    
private:
    TestCase( const TestCase &other ); 
    TestCase &operator=( const TestCase &other ); 
    
private:
    const std::string m_name;
};