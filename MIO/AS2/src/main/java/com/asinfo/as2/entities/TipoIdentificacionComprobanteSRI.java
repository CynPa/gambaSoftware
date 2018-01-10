/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="tipo_identificacion_comprobanteSRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_tipo_identificacion", "id_tipo_comprobanteSRI"})})
/*  17:    */ public class TipoIdentificacionComprobanteSRI
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="tipo_identificacion_comprobanteSRI", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_identificacion_comprobanteSRI")
/*  24:    */   @Column(name="id_tipo_identificacion_comprobanteSRI")
/*  25:    */   private int idTipoIdentificacionComprobanteSRI;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_tipo_identificacion", nullable=false)
/*  32:    */   private TipoIdentificacion tipoIdentificacion;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_tipo_comprobanteSRI", nullable=false)
/*  35:    */   private TipoComprobanteSRI tipoComprobanteSRI;
/*  36:    */   
/*  37:    */   public int getId()
/*  38:    */   {
/*  39: 80 */     return this.idTipoIdentificacionComprobanteSRI;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getIdTipoIdentificacionComprobanteSRI()
/*  43:    */   {
/*  44: 87 */     return this.idTipoIdentificacionComprobanteSRI;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setIdTipoIdentificacionComprobanteSRI(int idTipoIdentificacionComprobanteSRI)
/*  48:    */   {
/*  49: 95 */     this.idTipoIdentificacionComprobanteSRI = idTipoIdentificacionComprobanteSRI;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdOrganizacion()
/*  53:    */   {
/*  54:102 */     return this.idOrganizacion;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdOrganizacion(int idOrganizacion)
/*  58:    */   {
/*  59:110 */     this.idOrganizacion = idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdSucursal()
/*  63:    */   {
/*  64:117 */     return this.idSucursal;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdSucursal(int idSucursal)
/*  68:    */   {
/*  69:125 */     this.idSucursal = idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public TipoIdentificacion getTipoIdentificacion()
/*  73:    */   {
/*  74:132 */     return this.tipoIdentificacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/*  78:    */   {
/*  79:140 */     this.tipoIdentificacion = tipoIdentificacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public TipoComprobanteSRI getTipoComprobanteSRI()
/*  83:    */   {
/*  84:147 */     return this.tipoComprobanteSRI;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI)
/*  88:    */   {
/*  89:155 */     this.tipoComprobanteSRI = tipoComprobanteSRI;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoIdentificacionComprobanteSRI
 * JD-Core Version:    0.7.0.1
 */