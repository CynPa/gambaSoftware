/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.DecimalMin;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="actividad_material_orden_trabajo_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_material_orden_trabajo_mantenimiento", "id_detalle_orden_trabajo_mantenimiento"})})
/*  22:    */ public class ActividadMaterialOrdenTrabajoMantenimiento
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="actividad_material_orden_trabajo_mantenimiento", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="actividad_material_orden_trabajo_mantenimiento")
/*  30:    */   @Column(name="id_actividad_material_orden_trabajo_mantenimiento")
/*  31:    */   private int idActividadMaterialOrdenTrabajoMantenimiento;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private int idOrganizacion;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_material_orden_trabajo_mantenimiento", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private MaterialOrdenTrabajoMantenimiento materialOrdenTrabajoMantenimiento;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_detalle_orden_trabajo_mantenimiento", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento;
/*  46:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  47:    */   @Digits(integer=12, fraction=2)
/*  48:    */   @DecimalMin("0.00")
/*  49:    */   @NotNull
/*  50: 56 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  51:    */   
/*  52:    */   public int getId()
/*  53:    */   {
/*  54: 64 */     return this.idActividadMaterialOrdenTrabajoMantenimiento;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdActividadMaterialOrdenTrabajoMantenimiento()
/*  58:    */   {
/*  59: 68 */     return this.idActividadMaterialOrdenTrabajoMantenimiento;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdActividadMaterialOrdenTrabajoMantenimiento(int idActividadMaterialOrdenTrabajoMantenimiento)
/*  63:    */   {
/*  64: 72 */     this.idActividadMaterialOrdenTrabajoMantenimiento = idActividadMaterialOrdenTrabajoMantenimiento;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdSucursal()
/*  68:    */   {
/*  69: 76 */     return this.idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdSucursal(int idSucursal)
/*  73:    */   {
/*  74: 80 */     this.idSucursal = idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdOrganizacion()
/*  78:    */   {
/*  79: 84 */     return this.idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdOrganizacion(int idOrganizacion)
/*  83:    */   {
/*  84: 88 */     this.idOrganizacion = idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public MaterialOrdenTrabajoMantenimiento getMaterialOrdenTrabajoMantenimiento()
/*  88:    */   {
/*  89: 92 */     return this.materialOrdenTrabajoMantenimiento;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setMaterialOrdenTrabajoMantenimiento(MaterialOrdenTrabajoMantenimiento materialOrdenTrabajoMantenimiento)
/*  93:    */   {
/*  94: 96 */     this.materialOrdenTrabajoMantenimiento = materialOrdenTrabajoMantenimiento;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public DetalleOrdenTrabajoMantenimiento getDetalleOrdenTrabajoMantenimiento()
/*  98:    */   {
/*  99:100 */     return this.detalleOrdenTrabajoMantenimiento;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDetalleOrdenTrabajoMantenimiento(DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/* 103:    */   {
/* 104:104 */     this.detalleOrdenTrabajoMantenimiento = detalleOrdenTrabajoMantenimiento;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public BigDecimal getCantidad()
/* 108:    */   {
/* 109:108 */     return this.cantidad;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setCantidad(BigDecimal cantidad)
/* 113:    */   {
/* 114:112 */     this.cantidad = cantidad;
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.ActividadMaterialOrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */