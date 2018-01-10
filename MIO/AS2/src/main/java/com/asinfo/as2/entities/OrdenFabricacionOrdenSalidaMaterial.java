/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
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
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="orden_fabricacion_orden_salida_material", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_orden_salida_material", "id_orden_fabricacion"})})
/*  19:    */ public class OrdenFabricacionOrdenSalidaMaterial
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="orden_fabricacion_orden_salida_material", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="orden_fabricacion_orden_salida_material")
/*  27:    */   @Column(name="id_orden_fabricacion_orden_salida_material")
/*  28:    */   private int idOrdenFabricacionOrdenSalidaMaterial;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   @NotNull
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int idSucursal;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_orden_salida_material", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private OrdenSalidaMaterial ordenSalidaMaterial;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_orden_fabricacion", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private OrdenFabricacion ordenFabricacion;
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 63 */     return this.idOrdenFabricacionOrdenSalidaMaterial;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdOrdenFabricacionOrdenSalidaMaterial()
/*  50:    */   {
/*  51: 71 */     return this.idOrdenFabricacionOrdenSalidaMaterial;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdOrdenFabricacionOrdenSalidaMaterial(int idOrdenFabricacionOrdenSalidaMaterial)
/*  55:    */   {
/*  56: 75 */     this.idOrdenFabricacionOrdenSalidaMaterial = idOrdenFabricacionOrdenSalidaMaterial;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/*  60:    */   {
/*  61: 79 */     return this.ordenSalidaMaterial;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/*  65:    */   {
/*  66: 83 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public OrdenFabricacion getOrdenFabricacion()
/*  70:    */   {
/*  71: 87 */     return this.ordenFabricacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/*  75:    */   {
/*  76: 91 */     this.ordenFabricacion = ordenFabricacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdOrganizacion()
/*  80:    */   {
/*  81: 95 */     return this.idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdOrganizacion(int idOrganizacion)
/*  85:    */   {
/*  86: 99 */     this.idOrganizacion = idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdSucursal()
/*  90:    */   {
/*  91:103 */     return this.idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdSucursal(int idSucursal)
/*  95:    */   {
/*  96:107 */     this.idSucursal = idSucursal;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial
 * JD-Core Version:    0.7.0.1
 */