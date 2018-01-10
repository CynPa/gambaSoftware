/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.listener.DetalleProcesoImportacionListener;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EntityListeners;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Temporal;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_proceso_importacion", uniqueConstraints={})
/*  23:    */ @EntityListeners({DetalleProcesoImportacionListener.class})
/*  24:    */ public class DetalleProcesoImportacion
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="detalle_proceso_importacion", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_proceso_importacion")
/*  31:    */   @Column(name="id_detalle_proceso_importacion")
/*  32:    */   private int idDetalleProcesoImportacion;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="descripcion", nullable=true, length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Temporal(TemporalType.DATE)
/*  41:    */   @Column(name="fecha", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Date fecha;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_proceso_importacion", nullable=true)
/*  46:    */   private ProcesoImportacion procesoImportacion;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_factura_proveedor_importacion", nullable=true)
/*  49:    */   private FacturaProveedorImportacion facturaProveedorImportacion;
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53:101 */     return this.idDetalleProcesoImportacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdDetalleProcesoImportacion()
/*  57:    */   {
/*  58:110 */     return this.idDetalleProcesoImportacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdDetalleProcesoImportacion(int idDetalleProcesoImportacion)
/*  62:    */   {
/*  63:120 */     this.idDetalleProcesoImportacion = idDetalleProcesoImportacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68:129 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73:139 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78:148 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83:158 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getDescripcion()
/*  87:    */   {
/*  88:167 */     return this.descripcion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setDescripcion(String descripcion)
/*  92:    */   {
/*  93:177 */     this.descripcion = descripcion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Date getFecha()
/*  97:    */   {
/*  98:186 */     return this.fecha;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setFecha(Date fecha)
/* 102:    */   {
/* 103:196 */     this.fecha = fecha;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public ProcesoImportacion getProcesoImportacion()
/* 107:    */   {
/* 108:205 */     return this.procesoImportacion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setProcesoImportacion(ProcesoImportacion procesoImportacion)
/* 112:    */   {
/* 113:215 */     this.procesoImportacion = procesoImportacion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public FacturaProveedorImportacion getFacturaProveedorImportacion()
/* 117:    */   {
/* 118:224 */     return this.facturaProveedorImportacion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 122:    */   {
/* 123:234 */     this.facturaProveedorImportacion = facturaProveedorImportacion;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleProcesoImportacion
 * JD-Core Version:    0.7.0.1
 */