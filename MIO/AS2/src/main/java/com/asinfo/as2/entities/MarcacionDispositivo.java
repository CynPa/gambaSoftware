/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="marcacion_dispositivo")
/*  16:    */ public class MarcacionDispositivo
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="marcacion_dispositivo", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="marcacion_dispositivo")
/*  23:    */   @Column(name="id_MarcacionDispositivo", unique=true, nullable=false)
/*  24:    */   private int idMarcacionDispositivo;
/*  25:    */   @Column(name="ip", length=20)
/*  26:    */   @Size(max=20)
/*  27:    */   private String ip;
/*  28:    */   @Column(name="dispositivo", length=200)
/*  29:    */   @Size(max=200)
/*  30:    */   private String dispositivo;
/*  31:    */   @Column(name="marcacion", nullable=false, precision=12, scale=2)
/*  32:    */   @NotNull
/*  33: 52 */   private BigDecimal marcacion = BigDecimal.ZERO;
/*  34:    */   
/*  35:    */   public MarcacionDispositivo() {}
/*  36:    */   
/*  37:    */   public MarcacionDispositivo(String ip, String dispositivo, BigDecimal marcacion)
/*  38:    */   {
/*  39: 63 */     this.ip = ip;
/*  40: 64 */     this.dispositivo = dispositivo;
/*  41: 65 */     this.marcacion = marcacion;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 71 */     return 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdMarcacionDispositivo()
/*  50:    */   {
/*  51: 75 */     return this.idMarcacionDispositivo;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdMarcacionDispositivo(int idMarcacionDispositivo)
/*  55:    */   {
/*  56: 79 */     this.idMarcacionDispositivo = idMarcacionDispositivo;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getIp()
/*  60:    */   {
/*  61: 83 */     return this.ip;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIp(String ip)
/*  65:    */   {
/*  66: 87 */     this.ip = ip;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getDispositivo()
/*  70:    */   {
/*  71: 91 */     return this.dispositivo;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDispositivo(String dispositivo)
/*  75:    */   {
/*  76: 95 */     this.dispositivo = dispositivo;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public BigDecimal getMarcacion()
/*  80:    */   {
/*  81: 99 */     return this.marcacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setMarcacion(BigDecimal marcacion)
/*  85:    */   {
/*  86:103 */     this.marcacion = marcacion;
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MarcacionDispositivo
 * JD-Core Version:    0.7.0.1
 */