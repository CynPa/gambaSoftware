/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="actividad_mantenimiento_material", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_actividad_mantenimiento", "id_material"})})
/*  23:    */ public class ActividadMantenimientoMaterial
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="actividad_mantenimiento_material", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="actividad_mantenimiento_material")
/*  31:    */   @Column(name="id_actividad_mantenimiento_material")
/*  32:    */   private int idActividadMantenimientoMaterial;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private int idOrganizacion;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_actividad_mantenimiento", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private ActividadMantenimiento actividadMantenimiento;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_material", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private Producto material;
/*  47:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  48:    */   @Digits(integer=10, fraction=2)
/*  49:    */   @Min(0L)
/*  50:    */   @NotNull
/*  51: 57 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 65 */     return this.idActividadMantenimientoMaterial;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdActividadMantenimientoMaterial()
/*  59:    */   {
/*  60: 69 */     return this.idActividadMantenimientoMaterial;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdActividadMantenimientoMaterial(int idActividadMantenimientoMaterial)
/*  64:    */   {
/*  65: 73 */     this.idActividadMantenimientoMaterial = idActividadMantenimientoMaterial;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdSucursal()
/*  69:    */   {
/*  70: 77 */     return this.idSucursal;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdSucursal(int idSucursal)
/*  74:    */   {
/*  75: 81 */     this.idSucursal = idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdOrganizacion()
/*  79:    */   {
/*  80: 85 */     return this.idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdOrganizacion(int idOrganizacion)
/*  84:    */   {
/*  85: 89 */     this.idOrganizacion = idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ActividadMantenimiento getActividadMantenimiento()
/*  89:    */   {
/*  90: 93 */     return this.actividadMantenimiento;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setActividadMantenimiento(ActividadMantenimiento actividadMantenimiento)
/*  94:    */   {
/*  95: 97 */     this.actividadMantenimiento = actividadMantenimiento;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Producto getProducto()
/*  99:    */   {
/* 100:101 */     return this.material;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setProducto(Producto material)
/* 104:    */   {
/* 105:105 */     this.material = material;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public BigDecimal getCantidad()
/* 109:    */   {
/* 110:109 */     return this.cantidad;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setCantidad(BigDecimal cantidad)
/* 114:    */   {
/* 115:113 */     this.cantidad = cantidad;
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoMaterial
 * JD-Core Version:    0.7.0.1
 */