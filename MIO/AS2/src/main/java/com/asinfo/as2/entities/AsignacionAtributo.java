/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="asignacion_atributo")
/*  17:    */ public class AsignacionAtributo
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="asignacion_atributo", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="asignacion_atributo")
/*  24:    */   @Column(name="id_asignacion_atributo", unique=true, nullable=false)
/*  25:    */   private int idAsignacionAtributo;
/*  26:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  27:    */   @JoinColumn(name="id_atributo", nullable=false)
/*  28:    */   @NotNull
/*  29:    */   private Atributo atributo;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_conjunto_atributo", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   private ConjuntoAtributo conjuntoAtributo;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal")
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="orden", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int orden;
/*  41:    */   @Column(name="activo", nullable=false)
/*  42:    */   private boolean activo;
/*  43:    */   
/*  44:    */   public int getIdAsignacionAtributo()
/*  45:    */   {
/*  46: 69 */     return this.idAsignacionAtributo;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setIdAsignacionAtributo(int idAsignacionAtributo)
/*  50:    */   {
/*  51: 73 */     this.idAsignacionAtributo = idAsignacionAtributo;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Atributo getAtributo()
/*  55:    */   {
/*  56: 77 */     return this.atributo;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setAtributo(Atributo atributo)
/*  60:    */   {
/*  61: 81 */     this.atributo = atributo;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public ConjuntoAtributo getConjuntoAtributo()
/*  65:    */   {
/*  66: 85 */     return this.conjuntoAtributo;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setConjuntoAtributo(ConjuntoAtributo conjuntoAtributo)
/*  70:    */   {
/*  71: 89 */     this.conjuntoAtributo = conjuntoAtributo;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdOrganizacion()
/*  75:    */   {
/*  76: 93 */     return this.idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdOrganizacion(int idOrganizacion)
/*  80:    */   {
/*  81: 97 */     this.idOrganizacion = idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Integer getIdSucursal()
/*  85:    */   {
/*  86:101 */     return Integer.valueOf(this.idSucursal);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdSucursal(Integer idSucursal)
/*  90:    */   {
/*  91:105 */     this.idSucursal = idSucursal.intValue();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getOrden()
/*  95:    */   {
/*  96:109 */     return this.orden;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setOrden(int orden)
/* 100:    */   {
/* 101:113 */     this.orden = orden;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean getActivo()
/* 105:    */   {
/* 106:117 */     return this.activo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setActivo(boolean activo)
/* 110:    */   {
/* 111:121 */     this.activo = activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String toString()
/* 115:    */   {
/* 116:126 */     return this.atributo.getNombre();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getId()
/* 120:    */   {
/* 121:131 */     return this.idAsignacionAtributo;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.AsignacionAtributo
 * JD-Core Version:    0.7.0.1
 */