/*  1:   */ package com.asinfo.as2.entities.sri;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EntidadBase;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.GeneratedValue;
/*  7:   */ import javax.persistence.GenerationType;
/*  8:   */ import javax.persistence.Id;
/*  9:   */ import javax.persistence.Table;
/* 10:   */ import javax.persistence.TableGenerator;
/* 11:   */ 
/* 12:   */ @Entity
/* 13:   */ @Table(name="secuencia_documento_electronico")
/* 14:   */ public class SecuenciaDocumentoElectronico
/* 15:   */   extends EntidadBase
/* 16:   */ {
/* 17:   */   private static final long serialVersionUID = 1L;
/* 18:   */   @Id
/* 19:   */   @TableGenerator(name="secuencia_documento_floricola", initialValue=0, allocationSize=50)
/* 20:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="secuencia_documento_floricola")
/* 21:   */   @Column(name="id_secuencia_documento_electronico")
/* 22:   */   private int idSecuenciaDocumentoElectronico;
/* 23:   */   @Column(name="siguiente_secuencia")
/* 24:   */   private int siguienteSecuencia;
/* 25:   */   
/* 26:   */   public int getId()
/* 27:   */   {
/* 28:57 */     return this.idSecuenciaDocumentoElectronico;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getIdSecuenciaDocumentoElectronico()
/* 32:   */   {
/* 33:61 */     return this.idSecuenciaDocumentoElectronico;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setIdSecuenciaDocumentoElectronico(int idSecuenciaDocumentoElectronico)
/* 37:   */   {
/* 38:65 */     this.idSecuenciaDocumentoElectronico = idSecuenciaDocumentoElectronico;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getSiguienteSecuencia()
/* 42:   */   {
/* 43:69 */     return this.siguienteSecuencia;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setSiguienteSecuencia(int siguienteSecuencia)
/* 47:   */   {
/* 48:73 */     this.siguienteSecuencia = siguienteSecuencia;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.SecuenciaDocumentoElectronico
 * JD-Core Version:    0.7.0.1
 */