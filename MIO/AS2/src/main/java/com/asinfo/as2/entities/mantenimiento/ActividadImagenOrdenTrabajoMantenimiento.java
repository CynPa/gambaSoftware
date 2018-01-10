/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="actividad_imagen_orden_trabajo_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"nombre", "id_detalle_orden_trabajo_mantenimiento"})})
/*  20:    */ public class ActividadImagenOrdenTrabajoMantenimiento
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="actividad_imagen_orden_trabajo_mantenimiento", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="actividad_imagen_orden_trabajo_mantenimiento")
/*  28:    */   @Column(name="id_actividad_imagen_orden_trabajo_mantenimiento")
/*  29:    */   private int idActividadImagenOrdenTrabajoMantenimiento;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   @NotNull
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private int idOrganizacion;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_detalle_orden_trabajo_mantenimiento", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento;
/*  40:    */   @Column(name="nombre", nullable=false, length=100)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=2, max=100)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", length=200, nullable=true)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="archivo", length=200, nullable=false)
/*  48:    */   @Size(max=200)
/*  49:    */   private String archivo;
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53: 64 */     return this.idActividadImagenOrdenTrabajoMantenimiento;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdActividadImagenOrdenTrabajoMantenimiento()
/*  57:    */   {
/*  58: 68 */     return this.idActividadImagenOrdenTrabajoMantenimiento;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdActividadImagenOrdenTrabajoMantenimiento(int idActividadImagenOrdenTrabajoMantenimiento)
/*  62:    */   {
/*  63: 72 */     this.idActividadImagenOrdenTrabajoMantenimiento = idActividadImagenOrdenTrabajoMantenimiento;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdSucursal()
/*  67:    */   {
/*  68: 76 */     return this.idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdSucursal(int idSucursal)
/*  72:    */   {
/*  73: 80 */     this.idSucursal = idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdOrganizacion()
/*  77:    */   {
/*  78: 84 */     return this.idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdOrganizacion(int idOrganizacion)
/*  82:    */   {
/*  83: 88 */     this.idOrganizacion = idOrganizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public DetalleOrdenTrabajoMantenimiento getDetalleOrdenTrabajoMantenimiento()
/*  87:    */   {
/*  88: 92 */     return this.detalleOrdenTrabajoMantenimiento;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setDetalleOrdenTrabajoMantenimiento(DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/*  92:    */   {
/*  93: 96 */     this.detalleOrdenTrabajoMantenimiento = detalleOrdenTrabajoMantenimiento;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getNombre()
/*  97:    */   {
/*  98:100 */     return this.nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setNombre(String nombre)
/* 102:    */   {
/* 103:104 */     this.nombre = nombre;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getDescripcion()
/* 107:    */   {
/* 108:108 */     return this.descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDescripcion(String descripcion)
/* 112:    */   {
/* 113:112 */     this.descripcion = descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getArchivo()
/* 117:    */   {
/* 118:116 */     return this.archivo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setArchivo(String archivo)
/* 122:    */   {
/* 123:120 */     this.archivo = archivo;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.ActividadImagenOrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */