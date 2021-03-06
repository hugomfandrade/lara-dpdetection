#include <cppunit/TestSuccessListener.h>



TestSuccessListener::TestSuccessListener( SynchronizationObject *syncObject )
    : SynchronizedObject( syncObject )
    , m_success( true )
{
}


TestSuccessListener::~TestSuccessListener()
{
}


void 
TestSuccessListener::reset()
{
  ExclusiveZone zone( m_syncObject );
  m_success = true;
}


void 
TestSuccessListener::addFailure( const TestFailure & )
{
  ExclusiveZone zone( m_syncObject );
  m_success = false;
}


bool 
TestSuccessListener::wasSuccessful() const
{
  ExclusiveZone zone( m_syncObject );
  return m_success;
}

