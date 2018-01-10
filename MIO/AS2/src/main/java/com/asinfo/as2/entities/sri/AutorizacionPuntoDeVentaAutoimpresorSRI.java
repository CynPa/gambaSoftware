/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="autorizacion_punto_de_venta_autoimpresor_SRI")
/*  18:    */ public class AutorizacionPuntoDeVentaAutoimpresorSRI
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="autorizacion_punto_de_venta_autoimpresor_SRI", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="autorizacion_punto_de_venta_autoimpresor_SRI")
/*  25:    */   @Column(name="id_autorizacion_punto_de_venta_autoimpresor_SRI")
/*  26:    */   private int idAutorizacionPuntoDeVentaAutoimpresorSRI;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @ManyToOne
/*  32:    */   @JoinColumn(name="id_autorizacion_autoimpresor_SRI", nullable=true)
/*  33:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  34:    */   @ManyToOne
/*  35:    */   @JoinColumn(name="id_punto_de_venta", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private PuntoDeVenta puntoDeVenta;
/*  38:    */   @Column(name="activo", nullable=false)
/*  39:    */   private boolean activo;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 66 */     return this.idAutorizacionPuntoDeVentaAutoimpresorSRI;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdOrganizacion()
/*  47:    */   {
/*  48: 70 */     return this.idOrganizacion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdOrganizacion(int idOrganizacion)
/*  52:    */   {
/*  53: 74 */     this.idOrganizacion = idOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdSucursal()
/*  57:    */   {
/*  58: 78 */     return this.idSucursal;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdSucursal(int idSucursal)
/*  62:    */   {
/*  63: 82 */     this.idSucursal = idSucursal;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isActivo()
/*  67:    */   {
/*  68: 86 */     return this.activo;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setActivo(boolean activo)
/*  72:    */   {
/*  73: 90 */     this.activo = activo;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdAutorizacionPuntoDeVentaAutoimpresorSRI()
/*  77:    */   {
/*  78: 94 */     return this.idAutorizacionPuntoDeVentaAutoimpresorSRI;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdAutorizacionPuntoDeVentaAutoimpresorSRI(int idAutorizacionPuntoDeVentaAutoimpresorSRI)
/*  82:    */   {
/*  83: 98 */     this.idAutorizacionPuntoDeVentaAutoimpresorSRI = idAutorizacionPuntoDeVentaAutoimpresorSRI;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/*  87:    */   {
/*  88:102 */     return this.autorizacionAutoimpresorSRI;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/*  92:    */   {
/*  93:106 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public PuntoDeVenta getPuntoDeVenta()
/*  97:    */   {
/*  98:110 */     return this.puntoDeVenta;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 102:    */   {
/* 103:114 */     this.puntoDeVenta = puntoDeVenta;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.AutorizacionPuntoDeVentaAutoimpresorSRI
 * JD-Core Version:    0.7.0.1
 */