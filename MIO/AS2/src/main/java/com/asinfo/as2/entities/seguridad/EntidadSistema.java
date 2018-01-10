/*   1:    */ package com.asinfo.as2.entities.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="sistema", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"nombre"})})
/*  14:    */ public class EntidadSistema
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1L;
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="sistema", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="sistema")
/*  21:    */   @Column(name="id_sistema", unique=true, nullable=false)
/*  22:    */   private int idSistema;
/*  23:    */   @Column(name="nombre", nullable=false, length=50)
/*  24:    */   private String nombre;
/*  25:    */   
/*  26:    */   public EntidadSistema() {}
/*  27:    */   
/*  28:    */   public EntidadSistema(int idSistema, String nombre)
/*  29:    */   {
/*  30: 52 */     this.idSistema = idSistema;
/*  31: 53 */     this.nombre = nombre;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public EntidadSistema(String nombre)
/*  35:    */   {
/*  36: 61 */     this.nombre = nombre;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getId()
/*  40:    */   {
/*  41: 71 */     return this.idSistema;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getNombre()
/*  45:    */   {
/*  46: 80 */     return this.nombre;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setNombre(String nombre)
/*  50:    */   {
/*  51: 90 */     this.nombre = nombre;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIdSistema()
/*  55:    */   {
/*  56: 99 */     return this.idSistema;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdSistema(int idSistema)
/*  60:    */   {
/*  61:109 */     this.idSistema = idSistema;
/*  62:    */   }
/*  63:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.seguridad.EntidadSistema
 * JD-Core Version:    0.7.0.1
 */