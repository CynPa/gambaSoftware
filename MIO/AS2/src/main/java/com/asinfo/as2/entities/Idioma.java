/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Locale;
/*  5:   */ import javax.persistence.Column;
/*  6:   */ import javax.persistence.Entity;
/*  7:   */ import javax.persistence.GeneratedValue;
/*  8:   */ import javax.persistence.GenerationType;
/*  9:   */ import javax.persistence.Id;
/* 10:   */ import javax.persistence.Table;
/* 11:   */ import javax.persistence.TableGenerator;
/* 12:   */ 
/* 13:   */ @Entity
/* 14:   */ @Table(name="idioma")
/* 15:   */ public class Idioma
/* 16:   */   extends EntidadBase
/* 17:   */   implements Serializable
/* 18:   */ {
/* 19:   */   private static final long serialVersionUID = 1546336252990219043L;
/* 20:   */   @Id
/* 21:   */   @TableGenerator(name="idioma", initialValue=0, allocationSize=50)
/* 22:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="idioma")
/* 23:   */   @Column(name="id_idioma")
/* 24:   */   private int idIdioma;
/* 25:   */   private String nombre;
/* 26:   */   private Locale locale;
/* 27:   */   
/* 28:   */   public String getNombre()
/* 29:   */   {
/* 30:45 */     return this.nombre;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setNombre(String nombre)
/* 34:   */   {
/* 35:53 */     this.nombre = nombre;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Locale getLocale()
/* 39:   */   {
/* 40:60 */     return this.locale;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setLocale(Locale locale)
/* 44:   */   {
/* 45:68 */     this.locale = locale;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int getIdIdioma()
/* 49:   */   {
/* 50:75 */     return this.idIdioma;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void setIdIdioma(int idIdioma)
/* 54:   */   {
/* 55:83 */     this.idIdioma = idIdioma;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String toString()
/* 59:   */   {
/* 60:88 */     return this.nombre;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public int getId()
/* 64:   */   {
/* 65:93 */     return this.idIdioma;
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Idioma
 * JD-Core Version:    0.7.0.1
 */