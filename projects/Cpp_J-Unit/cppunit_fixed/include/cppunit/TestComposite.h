#pragma once 

#include <cppunit/Test.h>
#include <string>


/*! \brief A Composite of Tests.
 *
 * Base class for all test composites. Subclass this class if you need to implement
 * a custom TestSuite.
 * 
 * \see Test, TestSuite.
 */
class TestComposite : public Test
{
public:
  TestComposite( const std::string &name = "" );

  ~TestComposite() override;

  void run( TestResult *result ) override;

  int countTestCases() const override;
  
  std::string getName() const override;

private:
  TestComposite( const TestComposite &other );
  TestComposite &operator =( const TestComposite &other ); 

  virtual void doStartSuite( TestResult *controller );
  virtual void doRunChildTests( TestResult *controller );
  virtual void doEndSuite( TestResult *controller );

private:
  const std::string m_name;
};
