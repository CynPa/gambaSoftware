/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ 
/*  11:    */ @Entity
/*  12:    */ @Table(name="secuencia_as2")
/*  13:    */ public class SecuenciaAS2
/*  14:    */   extends EntidadBase
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 1L;
/*  17:    */   @Id
/*  18:    */   @TableGenerator(name="secuencia_as2", initialValue=0, allocationSize=50)
/*  19:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="secuencia_as2")
/*  20:    */   @Column(name="id_secuencia_as2")
/*  21:    */   private int idSecuenciaAS2;
/*  22:    */   @Column(name="nombre_secuencia", nullable=true, length=50)
/*  23:    */   private String nombreSecuencia;
/*  24:    */   @Column(name="siguiente_secuencia")
/*  25:    */   private Long siguienteSecuencia;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   
/*  29:    */   public int getId()
/*  30:    */   {
/*  31: 59 */     return this.idSecuenciaAS2;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getIdSecuenciaAS2()
/*  35:    */   {
/*  36: 68 */     return this.idSecuenciaAS2;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setIdSecuenciaAS2(int idSecuenciaAS2)
/*  40:    */   {
/*  41: 78 */     this.idSecuenciaAS2 = idSecuenciaAS2;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Long getSiguienteSecuencia()
/*  45:    */   {
/*  46: 87 */     return this.siguienteSecuencia;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setSiguienteSecuencia(Long siguienteSecuencia)
/*  50:    */   {
/*  51: 97 */     this.siguienteSecuencia = siguienteSecuencia;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getNombreSecuencia()
/*  55:    */   {
/*  56:101 */     return this.nombreSecuencia;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setNombreSecuencia(String nombreSecuencia)
/*  60:    */   {
/*  61:105 */     this.nombreSecuencia = nombreSecuencia;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdOrganizacion()
/*  65:    */   {
/*  66:109 */     return this.idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71:113 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SecuenciaAS2
 * JD-Core Version:    0.7.0.1
 */