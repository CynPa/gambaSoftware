/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Especialidad;
/*  4:   */ import javax.ejb.Stateless;
/*  5:   */ 
/*  6:   */ @Stateless
/*  7:   */ public class EspecialidadDao
/*  8:   */   extends AbstractDaoAS2<Especialidad>
/*  9:   */ {
/* 10:   */   public EspecialidadDao()
/* 11:   */   {
/* 12:27 */     super(Especialidad.class);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Especialidad cargarDetalle(int idEspecialidad)
/* 16:   */   {
/* 17:36 */     Especialidad especialidad = (Especialidad)buscarPorId(Integer.valueOf(idEspecialidad));
/* 18:   */     
/* 19:38 */     return especialidad;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EspecialidadDao
 * JD-Core Version:    0.7.0.1
 */