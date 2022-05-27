

/*! \brief Base class for synchronized object.
 *
 * Synchronized object are object which members are used concurrently by mutiple
 * threads.
 *
 * This class define the class SynchronizationObject which must be subclassed
 * to implement an actual lock.
 *
 * Each instance of this class holds a pointer on a lock object.
 *
 * See src/msvc6/MfcSynchronizedObject.h for an example.
 */
class SynchronizedObject
{
public:
  /*! \brief Abstract synchronization object (mutex)
   */
  class SynchronizationObject
  {
    public:
      SynchronizationObject() {}
      virtual ~SynchronizationObject() {}

      virtual void lock() {}
      virtual void unlock() {}
  };

  /*! Constructs a SynchronizedObject object.
   */
  SynchronizedObject( SynchronizationObject *syncObject = nullptr );

  /// Destructor.
  virtual ~SynchronizedObject();

protected:
  /*! \brief Locks a synchronization object in the current scope.
   */
  class ExclusiveZone
  {
    SynchronizationObject *m_syncObject;

  public:
    ExclusiveZone( SynchronizationObject *syncObject ) 
        : m_syncObject( syncObject )
    { 
      m_syncObject->lock();
    }

    ~ExclusiveZone() 
    { 
      m_syncObject->unlock ();
    }
  private:
    /// Prevents the use of the copy constructor.
    ExclusiveZone( const ExclusiveZone& );

    /// Prevents the use of the copy operator.
    ExclusiveZone& operator=( const ExclusiveZone& );
  };

  virtual void setSynchronizationObject( SynchronizationObject *syncObject );

protected:
  SynchronizationObject *m_syncObject;

private:
  /// Prevents the use of the copy constructor.
  SynchronizedObject( const SynchronizedObject &copy );

  /// Prevents the use of the copy operator.
  void operator =( const SynchronizedObject &copy );
};