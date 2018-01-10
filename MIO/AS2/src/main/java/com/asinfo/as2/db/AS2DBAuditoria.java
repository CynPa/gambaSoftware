package com.asinfo.as2.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AS2DBAuditoria
{
  @PersistenceContext(name="AS2PU")
  protected EntityManager em;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.db.AS2DBAuditoria
 * JD-Core Version:    0.7.0.1
 */